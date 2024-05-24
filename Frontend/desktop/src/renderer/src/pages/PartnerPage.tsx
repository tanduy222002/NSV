import { useQuery } from '@tanstack/react-query';
import { Pagination, SearchBar, UserInfo } from '@renderer/components';
import {
    useDeferredState,
    useLocalStorage,
    usePagination
} from '@renderer/hooks';
import partnerIconSrc from '@renderer/assets/partner-icon.png';
import { ColumnType } from '@renderer/components/TableView';
import { TableSkeleton, Button, TableView } from '@renderer/components';
import { getPartnerList } from '@renderer/services/api';
import { useNavigate } from 'react-router-dom';
import { formatNumber } from '@renderer/utils/formatText';
import { useState } from 'react';

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
    const [maxPage, setMaxPage] = useState(1);
    const { currentPage, goNext, goBack, goToPage } = usePagination(maxPage);

    const [searchValue, setSearchValue] = useDeferredState('');

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();
    const { isFetching, data } = useQuery({
        queryKey: ['partners', currentPage, searchValue],
        queryFn: async () => {
            const response = await getPartnerList({
                token: accessToken,
                pageIndex: currentPage,
                name: searchValue.length > 0 ? searchValue : undefined
            });
            setMaxPage(currentPage);
            return response;
        }
    });

    const navigate = useNavigate();
    const goToPartnerDetailPage = (id: number | string) =>
        navigate(`/partner/${id}`);
    const goToCreatePartnerPage = () => navigate('/partner/create');

    const mapPartnerTable = (partners) =>
        partners?.map((partner) => ({
            id: partner?.id,
            name: partner?.name,
            phoneNumber: partner?.phone,
            address: partner?.address_string,
            transactionValue: `${formatNumber(partner?.total_transaction_amount ?? 0)} VND`
        })) ?? [];

    console.log('partner data: ', data);
    return (
        <div className="w-full p-10">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <img src={partnerIconSrc} />
                <h1 className="font-semibold text-xl">Đối tác</h1>
            </div>
            <Button
                className="mb-5 px-2 py-1 border border-emerald-500 rounded-md hover:bg-emerald-50 text-emerald-500 text-base font-semibold w-fit"
                text="Thêm đối tác"
                action={goToCreatePartnerPage}
            />
            {/* <div className="flex gap-2 mb-5">
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
            </div> */}
            <div className="flex flex-col gap-4 w-[1100px]">
                <SearchBar
                    className="ml-auto"
                    placeHolder="Tìm phiếu..."
                    updateSearchValue={setSearchValue}
                />
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <>
                        <TableView
                            columns={partnerTableConfig}
                            items={mapPartnerTable(data?.content)}
                            viewAction={goToPartnerDetailPage}
                        />
                        <Pagination
                            currentPage={currentPage}
                            maxPage={maxPage}
                            goBack={goBack}
                            goNext={goNext}
                            goToPage={goToPage}
                        />
                    </>
                )}
            </div>
        </div>
    );
};

export default PartnerPage;
