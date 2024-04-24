import { useQuery } from '@tanstack/react-query';
import { UserInfo } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import partnerIconSrc from '@renderer/assets/partner-icon.png';
import { ColumnType } from '@renderer/components/TableView';
import { TableSkeleton, Button, TableView } from '@renderer/components';
import { searchPartner } from '@renderer/services/api';

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
        title: 'Địa chỉ',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Liên hệ',
        sortable: false,
        type: ColumnType.Action
    }
];

const PartnerPage = () => {
    const { isFetching, data } = useQuery({
        queryKey: ['partners'],
        queryFn: async () => {
            return await searchPartner({ token: accessToken, pageIndex: 1 });
        }
    });
    console.log('partner data: ', data);
    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();
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
            <div className="w-full max-w-[900px] mx-auto">
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <TableView
                        columns={partnerTableConfig}
                        items={data?.content}
                    />
                )}
            </div>
        </div>
    );
};

export default PartnerPage;
