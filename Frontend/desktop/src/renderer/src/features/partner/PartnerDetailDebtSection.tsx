import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { useLocalStorage } from '@renderer/hooks';
import { getPartnerDebtDetail } from '@renderer/services/api';
import { SelectInput, TableSkeleton, TableView } from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { formatDate, formatNumber } from '@renderer/utils/formatText';
import { useState } from 'react';

const debtTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tên đơn',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Ngày tạo',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Ngày hết hạn',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Giá trị',
        sortable: false,
        type: ColumnType.Text,
        stylable: true
    },
    {
        title: 'Mô tả',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Trạng thái',
        sortable: false,
        type: ColumnType.Text,
        stylable: true
    }
];

enum PaidStatus {
    All = 'All',
    Paid = 'Paid',
    Unpaid = 'Unpaid'
}

const PaidStatusText = {
    All: 'Tất cả',
    Paid: 'Đã thanh toán',
    Unpaid: 'Chưa thanh toán'
};

const PartnerDetailDebtSection = () => {
    const { id } = useParams();

    const [paidStatus, setPaidStatus] = useState(PaidStatus.All);
    const updatePaidStatus = (statusText: string) => {
        if (statusText === PaidStatusText.All) setPaidStatus(PaidStatus.All);
        if (statusText === PaidStatusText.Paid) setPaidStatus(PaidStatus.Paid);
        if (statusText === PaidStatusText.Unpaid)
            setPaidStatus(PaidStatus.Unpaid);
    };

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['partner-debt', id, paidStatus],
        queryFn: () =>
            getPartnerDebtDetail({
                token: accessToken,
                partnerId: id as string,
                isPaid:
                    paidStatus === PaidStatus.All
                        ? undefined
                        : paidStatus === PaidStatus.Paid
            })
    });

    const mapDebtTable = (debts) =>
        debts.map((debt) => ({
            id: debt?.id,
            name: debt?.name,
            createDate: formatDate(debt?.create_date),
            dueDate: formatDate(debt?.due_date),
            value: `${formatNumber(debt?.value)} VND`,
            description: debt?.description,
            paidStatus: debt?.is_paid ? 'Đã thanh toán' : 'Chưa thanh toán',
            textColor: debt?.is_paid ? 'text-emerald-500' : 'text-red-500'
        })) ?? [];

    if (!isFetching) console.log('partner debt: ', data);

    return (
        <div>
            <h1 className="text-lg mb-5 font-semibold text-sky-800">Công nợ</h1>
            <div className="w-full max-w-[900px]">
                <div className="mb-5 w-fit ml-auto min-w-[180px]">
                    <SelectInput
                        placeHolder={PaidStatusText.All}
                        selectedValue={PaidStatusText[paidStatus]}
                        values={Object.values(PaidStatusText)}
                        onSelect={updatePaidStatus}
                    />
                </div>
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <TableView
                        columns={debtTableConfig}
                        items={mapDebtTable(data?.content)}
                    />
                )}
            </div>
        </div>
    );
};

export default PartnerDetailDebtSection;
