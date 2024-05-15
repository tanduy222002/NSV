import { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { GrUserWorker } from 'react-icons/gr';
import { getEmployeeList } from '@renderer/services/api';
import { useLocalStorage } from '@renderer/hooks';
import {
    Button,
    ConfirmationPopup,
    InformationPopup,
    TableSkeleton,
    UserInfo
} from '@renderer/components';
import TableView, { ColumnType } from '@renderer/components/TableView';
import { InfoPopup, ResultPopup } from '@renderer/types/common';
import {
    AccountRoleText,
    AccountStatusText,
    AccountStatusTextColor,
    DeleteEmployeeResult,
    deleteEmployeeConfirmPopupData
} from '@renderer/constants/employee';
import { deleteEmployee } from '@renderer/services/api';
import { EmployeeAccountStatus } from '@renderer/types/employee';
import { cn } from '@renderer/utils/util';

const employeeTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tên',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Email',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Loại tài khoản',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Trạng thái',
        sortable: false,
        type: ColumnType.Text,
        stylable: true
    },
    {
        title: 'Thao tác',
        sortable: false,
        type: ColumnType.Action
    }
];

const EmployeePage = () => {
    const deleteEmployeeId = useRef<number | null>(null);
    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    const [infoPopup, setInfoPopup] = useState<InfoPopup | null>(null);

    const [accountStatus, setAccountStatus] = useState<EmployeeAccountStatus>(
        EmployeeAccountStatus.All
    );

    // pass to row delete action, invoke and save id to delete id ref
    const openDeleteEmployeeConfirmPopup = (employeeId) => {
        deleteEmployeeId.current = employeeId;
        setInfoPopup(deleteEmployeeConfirmPopupData);
    };
    const closeInfoPopup = () => setInfoPopup(null);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();
    const { isFetching, data, refetch } = useQuery({
        queryKey: ['employees', accountStatus],
        queryFn: () =>
            getEmployeeList({
                token: accessToken,
                pageIndex: 1,
                status:
                    accountStatus === EmployeeAccountStatus.All
                        ? undefined
                        : accountStatus
            })
    });

    const navigate = useNavigate();
    const goToEmployeeDetailPage = (id: number | string) =>
        navigate(`/employee/${id}`);
    const goToCreateEmployeePage = () => navigate('/employee/create');

    const handleDeleteEmployee = async () => {
        const deleteId = deleteEmployeeId.current;
        if (!deleteId) return;
        const response = await deleteEmployee({
            token: accessToken,
            employeeId: String(deleteId)
        });
        if (response?.status >= 400) {
            setResultPopup(DeleteEmployeeResult.Error);
        }
        if (response?.status === 200) {
            setResultPopup(DeleteEmployeeResult.Success);
        }
        closeInfoPopup();
        refetch();
    };

    const mapEmployeeTable = (employees) =>
        employees?.map((employee) => ({
            id: employee?.employee_id,
            name: employee?.name,
            email: employee?.email,
            role: AccountRoleText[employee?.roles[0]],
            accountStatus: AccountStatusText[employee?.status],
            textColor: AccountStatusTextColor[employee?.status]
        })) ?? [];

    console.log('employee data: ', data);
    return (
        <div className="w-full p-10">
            {infoPopup && (
                <ConfirmationPopup
                    title={infoPopup.title}
                    body={infoPopup.body}
                    confirmAction={handleDeleteEmployee}
                    cancelAction={closeInfoPopup}
                />
            )}
            {resultPopup && (
                <InformationPopup
                    title={resultPopup.title}
                    body={resultPopup.body}
                    popupType={resultPopup.popupType}
                    closeAction={closeResultPopup}
                />
            )}
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <GrUserWorker className="w-[20px] h-[20px] text-sky-800" />
                <h1 className="font-semibold text-xl">Đối tác</h1>
            </div>
            <Button
                className="mb-5 px-2 py-1 border border-emerald-500 rounded-md hover:bg-emerald-50 text-emerald-500 text-base font-semibold w-fit"
                text="Thêm đối tác"
                action={goToCreateEmployeePage}
            />
            <div className="flex gap-2 mb-5">
                <Button
                    className={
                        (cn(
                            'px-2 py-1 rounded-md border text-base font-semibold w-fit'
                        ),
                        accountStatus === EmployeeAccountStatus.All
                            ? 'bg-sky-800 text-white'
                            : 'border-sky-800 text-sky-800 hover:bg-sky-100')
                    }
                    text="Toàn bộ"
                    action={() => setAccountStatus(EmployeeAccountStatus.All)}
                />
                <Button
                    className={
                        (cn(
                            'px-2 py-1 rounded-md border text-base font-semibold w-fit'
                        ),
                        accountStatus === EmployeeAccountStatus.Active
                            ? 'bg-sky-800 text-white'
                            : 'border-sky-800 text-sky-800 hover:bg-sky-100')
                    }
                    text="Hoạt động"
                    action={() =>
                        setAccountStatus(EmployeeAccountStatus.Active)
                    }
                />
                <Button
                    className={
                        (cn(
                            'px-2 py-1 rounded-md border text-base font-semibold w-fit'
                        ),
                        accountStatus === EmployeeAccountStatus.Suspended
                            ? 'bg-sky-800 text-white'
                            : 'border-sky-800 text-sky-800 hover:bg-sky-100')
                    }
                    text="Tạm dừng"
                    action={() =>
                        setAccountStatus(EmployeeAccountStatus.Suspended)
                    }
                />
            </div>
            <div className="w-fit max-w-[1200px]">
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <TableView
                        columns={employeeTableConfig}
                        items={mapEmployeeTable(data?.content)}
                        viewAction={goToEmployeeDetailPage}
                        deleteAction={openDeleteEmployeeConfirmPopup}
                    />
                )}
            </div>
        </div>
    );
};

export default EmployeePage;
