import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { useLocalStorage } from '@renderer/hooks';
import { getPartnerTransactionDetail } from '@renderer/services/api';
import { TableSkeleton, TableView, SelectInput } from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { useState } from 'react';
import { formatDate, formatNumber } from '@renderer/utils/formatText';

const transactionTableConfig = [
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
        title: 'Ngày giao',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Sản phẩm',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Mô tả',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Khối lượng',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Giá trị',
        sortable: false,
        type: ColumnType.Text
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

const PartnerDetailTransactionSection = () => {
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
        queryKey: ['partner-transaction', id, paidStatus],
        queryFn: () =>
            getPartnerTransactionDetail({
                token: accessToken,
                partnerId: id as string,
                isPaid:
                    paidStatus === PaidStatus.All
                        ? undefined
                        : paidStatus === PaidStatus.Paid
            })
    });

    const mapTransactionTable = (transactions) =>
        transactions?.map((transaction) => ({
            id: transaction?.id,
            name: transaction?.name,
            transferDate: formatDate(transaction?.transfer_date),
            numberOfProducts: `${transaction?.number_of_products ?? 0} loại sản phẩm`,
            description: transaction?.description,
            weight: `${formatNumber(transaction?.weight ?? 0)} kg`,
            value: `${formatNumber(transaction?.value ?? 0)} VND`
        })) ?? [];

    if (!isFetching) console.log('partner transaction: ', data);

    return (
        <div className="w-full max-w-[1050px]">
            <h1 className="text-lg mb-5 font-semibold text-sky-800">
                Lịch sử giao dịch
            </h1>
            <div className="ml-auto w-full max-w-[150px]">
                <SelectInput
                    placeHolder={PaidStatusText.All}
                    selectedValue={PaidStatusText[paidStatus]}
                    values={Object.values(PaidStatusText)}
                    onSelect={updatePaidStatus}
                />
            </div>
            <div className="mt-5">
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <TableView
                        columns={transactionTableConfig}
                        items={mapTransactionTable(data?.content)}
                    />
                )}
            </div>
        </div>
    );
};

export default PartnerDetailTransactionSection;
