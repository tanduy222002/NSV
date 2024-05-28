import { useQuery } from '@tanstack/react-query';
import { useNavigate, useParams } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { GrUserWorker } from 'react-icons/gr';
import {
    UserInfo,
    DataField,
    Button,
    InformationPopup,
    ConfirmationPopup,
    PageLoading
} from '@renderer/components';
import {
    getEmployeeDetail,
    updateEmployeeAccountStatus,
    updateEmployeeRoles
} from '@renderer/services/api';
import { useLocalStorage } from '@renderer/hooks';
import { FaUserCog } from 'react-icons/fa';
import { FaLock } from 'react-icons/fa';
import { FaArrowUp } from 'react-icons/fa6';
import { FaArrowDown } from 'react-icons/fa';
import { MdOutlineEmail } from 'react-icons/md';
import { InfoPopup, ResultPopup } from '@renderer/types/common';
import { useState } from 'react';
import { EmployeeAccountStatus, EmployeeRole } from '@renderer/types/employee';
import {
    AccountRoleText,
    AccountStatusText,
    AccountStatusTextColor,
    UpdateAccountRolesResult,
    UpdateAccountStatusResult,
    updateAccountRolesResultPopupData,
    updateAccountStatusConfirmPopupData
} from '@renderer/constants/employee';
import { FaLockOpen } from 'react-icons/fa';
import { isEqual } from 'lodash';

const EmployeeDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const goToEmployeePage = () => navigate('/employee');

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const {
        data: employee,
        isFetching,
        refetch
    } = useQuery({
        queryKey: ['partner', id],
        queryFn: () =>
            getEmployeeDetail({
                token: accessToken,
                employeeId: id as string
            })
    });

    const [infoPopup, setInfoPopup] = useState<InfoPopup | null>(null);
    const closeInfoPopup = () => setInfoPopup(null);
    const openUpdateAccountConfirmPopup = () => {
        setInfoPopup(updateAccountStatusConfirmPopupData);
    };
    const handleEmployeeAccountStatusUpdate = async () => {
        const response = await updateEmployeeAccountStatus({
            token: accessToken,
            employeeId: id as string,
            status:
                employee?.status === EmployeeAccountStatus.Active
                    ? EmployeeAccountStatus.Suspended
                    : EmployeeAccountStatus.Active
        });
        if (response?.status >= 400) {
            setResultPopup(UpdateAccountStatusResult.Error);
            return;
        }
        setResultPopup(UpdateAccountStatusResult.Success);
    };

    const openUpdateRolesConfirmPopup = () =>
        setInfoPopup(updateAccountRolesResultPopupData);
    const handleUpdateEmployeeRoles = async () => {
        const response = await updateEmployeeRoles({
            token: accessToken,
            employeeId: id as string,
            role: employee?.roles.includes(EmployeeRole.Manager)
                ? EmployeeRole.Employee
                : EmployeeRole.Manager
        });
        if (response?.status >= 400) {
            setResultPopup(UpdateAccountRolesResult.Error);
            return;
        }
        setResultPopup(UpdateAccountRolesResult.Success);
    };

    const handleConfirmAction = async () => {
        // close confirm popup
        closeInfoPopup();
        // update employee data
        if (isEqual(infoPopup, updateAccountStatusConfirmPopupData)) {
            await handleEmployeeAccountStatusUpdate();
        } else {
            await handleUpdateEmployeeRoles();
        }
        // refetch latest data
        refetch();
    };

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    if (!isFetching) console.log('employee detail: ', employee);

    return (
        <div className="w-full p-10">
            {infoPopup && (
                <ConfirmationPopup
                    title={infoPopup.title}
                    body={infoPopup.body}
                    confirmAction={handleConfirmAction}
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
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToEmployeePage}
                />
                <GrUserWorker className="w-[20px] h-[20px] text-sky-800" />
                <h1 className="text-xl font-semibold">Nhân viên</h1>
            </div>
            {isFetching ? (
                <PageLoading />
            ) : (
                <div className="space-y-5 max-w-[450px]">
                    <DataField
                        name="Tên nhân viên"
                        icon={<FaUserCog />}
                        disabled={false}
                        value={employee?.name}
                        defaultValue="Tên nhân viên"
                    />
                    <DataField
                        name="Email"
                        icon={<MdOutlineEmail />}
                        disabled={false}
                        value={employee?.name}
                        defaultValue="Email"
                    />
                    <DataField
                        name="Trạng thái"
                        disabled={false}
                        value={AccountStatusText[employee?.status]}
                        defaultValue="Trạng thái"
                        textColor={AccountStatusTextColor[employee?.status]}
                    />
                    <div className="flex flex-col gap-3 text-sky-800 font-semibold">
                        <h3>Loại tài khoản</h3>
                        <div className="flex items-center gap-4">
                            {employee?.roles.map((role, i) => (
                                <div
                                    className="text-sm border border-sky-800 rounded-md p-1"
                                    key={i}
                                >
                                    {AccountRoleText[role]}
                                </div>
                            ))}
                        </div>
                    </div>
                    <div className="flex items-center gap-4 w-fit">
                        <Button
                            icon={
                                employee?.data?.roles.includes(
                                    EmployeeRole.Manager
                                ) ? (
                                    <FaArrowDown />
                                ) : (
                                    <FaArrowUp />
                                )
                            }
                            className="mx-auto px-2 py-1 border rounded-md border-emerald-500 text-emerald-500 hover:bg-emerald-100 font-semibold w-fit"
                            text={
                                employee?.roles.includes(EmployeeRole.Manager)
                                    ? 'Hạ quyền'
                                    : 'Nâng quyền'
                            }
                            action={openUpdateRolesConfirmPopup}
                        />
                        <Button
                            icon={
                                employee?.status ===
                                EmployeeAccountStatus.Active ? (
                                    <FaLock />
                                ) : (
                                    <FaLockOpen />
                                )
                            }
                            className="mx-auto px-2 py-1 border rounded-md border-amber-300 text-amber-300 hover:bg-amber-50 font-semibold w-fit"
                            text={
                                employee?.status ===
                                EmployeeAccountStatus.Active
                                    ? 'Khóa tài khoản'
                                    : 'Mở khóa tài khoản'
                            }
                            action={openUpdateAccountConfirmPopup}
                        />
                    </div>
                </div>
            )}
        </div>
    );
};

export default EmployeeDetailPage;
