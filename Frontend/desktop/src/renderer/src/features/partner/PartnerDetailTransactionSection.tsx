import { useQuery } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { useLocalStorage } from '@renderer/hooks';
import { getPartnerTransactionDetail } from '@renderer/services/api';
import { TableSkeleton, TableView, SelectInput } from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { useState } from 'react';

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
        transactions.map((transaction) => ({
            id: transaction?.id,
            name: transaction?.name,
            transferDate: transaction?.transfer_date,
            numberOfProducts: `${transaction?.number_of_products} loại sản phẩm`,
            description: transaction?.description,
            weight: transaction?.weight,
            value: transaction?.value
        })) ?? [];

    if (!isFetching) console.log('partner transaction: ', data);

    return (
        <div>
            <h1 className="text-lg mb-5 font-semibold text-sky-800">
                Lịch sử giao dịch
            </h1>
            <div className="ml-auto w-full max-w-[150px]">
                <SelectInput
                    placeHolder={PaidStatusText.All}
                    selectedValue={PaidStatusText[paidStatus]}
                    values={Object.values(PaidStatusText)}
                    onSelect={setPaidStatus}
                />
            </div>
            {isFetching ? (
                <TableSkeleton />
            ) : (
                <TableView
                    columns={transactionTableConfig}
                    items={mapTransactionTable(data?.content)}
                />
            )}
        </div>
    );
};

export default PartnerDetailTransactionSection;
