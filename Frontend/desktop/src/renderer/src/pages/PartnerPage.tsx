import { useQuery } from '@tanstack/react-query';
import { UserInfo } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import partnerIconSrc from '@renderer/assets/partner-icon.png';
import { ColumnType } from '@renderer/components/TableView';
import { TableSkeleton, Button, TableView } from '@renderer/components';
import { getPartnerList } from '@renderer/services/api';
import { useNavigate } from 'react-router-dom';

const partnerTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tên đối tác',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Liên hệ',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Địa chỉ',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tổng giao dịch',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Thao tác',
        sortable: false,
        type: ColumnType.Action
    }
];

const PartnerPage = () => {
    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();
    const { isFetching, data } = useQuery({
        queryKey: ['partners'],
        queryFn: () => getPartnerList({ token: accessToken, pageIndex: 1 })
    });

    const navigate = useNavigate();
    const goToPartnerDetailPage = (id: number | string) =>
        navigate(`/partner/${id}`);

    const mapPartnerTable = (partners) =>
        partners.map((partner) => ({
            id: partner?.id,
            name: partner?.name,
            phoneNumber: partner?.phone,
            address: partner?.address_string,
            transactionValue: partner?.total_transaction_amount
        }));

    console.log('partner data: ', data);
    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <img src={partnerIconSrc} />
                <h1 className="font-semibold text-xl">Đối tác</h1>
            </div>
            <Button
                className="mb-5 px-2 py-1 border border-emerald-600 rounded-md text-emerald-600 text-base font-semibold w-fit"
                text="Thêm đối tác"
                // action={confirmAction}
            />
            <div className="flex gap-2 mb-5">
                <Button
                    className="px-2 py-1 border border-sky-700 rounded-md text-sky-700 text-base font-semibold w-fit"
                    text="Toàn bộ"
                    // action={confirmAction}
                />
                <Button
                    className="px-2 py-1 border border-sky-700 rounded-md text-sky-700 text-base font-semibold w-fit"
                    text="Nhà cung cấp"
                    // action={confirmAction}
                />
                <Button
                    className="px-2 py-1 border border-sky-700 rounded-md text-sky-700 text-base font-semibold w-fit"
                    text="Người mua"
                    // action={confirmAction}
                />
            </div>
            <div className="w-full max-w-[900px]">
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <TableView
                        columns={partnerTableConfig}
                        items={mapPartnerTable(data?.content)}
                        viewAction={goToPartnerDetailPage}
                    />
                )}
            </div>
        </div>
    );
};

export default PartnerPage;
