import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useLocalStorage } from '@renderer/hooks';
import { getPartnerDebtDetail } from '@renderer/services/api';
import { TableSkeleton, TableView } from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';

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
        type: ColumnType.Text
    },
    {
        title: 'Mô tả',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Trạng thái',
        sortable: false,
        type: ColumnType.Text
    }
];

const PartnerDetailDebtSection = () => {
    const { id } = useParams();

    // const [paidStatus, setPaidStatus] = useState(PaidStatus.All);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['partner-debt', id],
        queryFn: () =>
            getPartnerDebtDetail({
                token: accessToken,
                partnerId: id as string
            })
    });

    const mapDebtTable = (debts) =>
        debts.map((debt) => ({
            id: debt?.id,
            name: debt?.name,
            createDate: debt?.create_date,
            dueDate: debt?.due_date,
            value: `${debt?.value} VND`,
            description: debt?.description,
            paidStatus: debt?.isPaid ? 'Đã thanh toán' : 'Chưa thanh toán'
        })) ?? [];

    if (!isFetching) console.log('partner debt: ', data);

    return (
        <div>
            <h1 className="text-lg mb-5 font-semibold text-sky-800">Công nợ</h1>
            {/* <div className="ml-auto w-full max-w-[150px]">
                    <SelectInput
                        placeHolder={PaidStatusText.All}
                        selectedValue={PaidStatusText[paidStatus]}
                        values={Object.values(PaidStatusText)}
                        onSelect={setPaidStatus}
                    />
                </div> */}
            {isFetching ? (
                <TableSkeleton />
            ) : (
                <TableView
                    columns={debtTableConfig}
                    items={mapDebtTable(data?.content)}
                />
            )}
        </div>
    );
};

export default PartnerDetailDebtSection;
