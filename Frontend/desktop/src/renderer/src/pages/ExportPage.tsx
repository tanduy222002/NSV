import { useQuery } from '@tanstack/react-query';
import { useCallback, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';
import { ColumnType } from '@renderer/components/TableView';
import { TicketStatus } from '@renderer/types/import';
import {
    UserInfo,
    Button,
    TableView,
    TableSkeleton,
    Pagination,
    SearchBar
} from '@renderer/components';
import {
    useDeferredState,
    useLocalStorage,
    usePagination
} from '@renderer/hooks';
import { searchExportTicket } from '@renderer/services/api';
import { formatNumber, formatDate } from '@renderer/utils/formatText';
import { cn } from '@renderer/utils/util';

const exportTicketTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tên phiếu',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Sản phẩm',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Khối lượng',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Ngày nhập',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Thao tác',
        sortable: false,
        type: ColumnType.Action
    }
];

const ExportPage = () => {
    const navigate = useNavigate();
    const goToCreateExportTicketPage = () => navigate('/export/create');
    const goToExportTicketDetailPage = (ticketId: number | string) =>
        navigate(`/export/${ticketId}`);

    const [ticketStatus, setTicketStatus] = useState<TicketStatus>(
        TicketStatus.All
    );

    const [searchValue, setSearchValue] = useDeferredState('');

    const updateTicketStatus = (status: TicketStatus) =>
        setTicketStatus(status);

    const mapTicketTable = useCallback(
        (tickets: any[]) =>
            tickets?.map((ticket) => ({
                id: ticket?.id,
                name: ticket?.name,
                productCount: `${ticket?.number_of_products} sản phẩm`,
                weight: `${formatNumber(ticket?.weight)} kg`,
                exportDate: formatDate(ticket?.transfer_date)
            })) ?? [],
        []
    );

    const [maxPage, setMaxPage] = useState(1);
    const { currentPage, goNext, goBack, goToPage } = usePagination(maxPage);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();
    const { data, isFetching } = useQuery({
        queryKey: ['export', ticketStatus, searchValue, currentPage],
        queryFn: async () => {
            const response = await searchExportTicket({
                token: accessToken,
                name: searchValue,
                pageIndex: currentPage,
                pageSize: 10,
                status: ticketStatus
            });
            setMaxPage(response?.total_page);
            return response;
        }
    });

    if (!isFetching) {
        console.log('data: ', data);
    }
    return (
        <div className="w-full p-10">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <img src={warehouseIconSrc} />
                <h1 className="font-semibold text-xl">Phiếu xuất</h1>
            </div>
            <Button
                className="mb-5 px-2 py-1 border border-emerald-600 rounded-md text-emerald-600 hover:bg-emerald-50 text-base font-semibold w-fit"
                text="Tạo phiếu"
                action={goToCreateExportTicketPage}
            />
            <div className="flex items-center gap-2">
                <Button
                    className={cn(
                        'mb-5 px-2 py-1 text-base font-semibold w-fit',
                        ticketStatus === TicketStatus.All
                            ? 'text-white bg-sky-800'
                            : ' border border-sky-800 rounded-md text-sky-800 hover:bg-sky-100'
                    )}
                    text="Toàn bộ"
                    action={() => updateTicketStatus(TicketStatus.All)}
                />
                <Button
                    className={cn(
                        'mb-5 px-2 py-1 text-base font-semibold w-fit',
                        ticketStatus === TicketStatus.Approved
                            ? 'text-white bg-emerald-500'
                            : ' border border-emerald-600 rounded-md text-emerald-600 hover:bg-emerald-50 '
                    )}
                    text="Đã duyệt"
                    action={() => updateTicketStatus(TicketStatus.Approved)}
                />
                <Button
                    className={cn(
                        'mb-5 px-2 py-1 text-base font-semibold w-fit',
                        ticketStatus === TicketStatus.Pending
                            ? 'text-white bg-amber-300'
                            : 'border border-amber-300 rounded-md text-amber-300 hover:bg-amber-50 '
                    )}
                    text="Chờ duyệt"
                    action={() => updateTicketStatus(TicketStatus.Pending)}
                />
                <Button
                    className={cn(
                        'mb-5 px-2 py-1 text-base font-semibold w-fit',
                        ticketStatus === TicketStatus.Rejected
                            ? 'text-white bg-red-500'
                            : 'border border-red-500 rounded-md text-red-500 hover:bg-red-100'
                    )}
                    text="Từ chối"
                    action={() => updateTicketStatus(TicketStatus.Rejected)}
                />
            </div>
            <div className="flex flex-col gap-4 w-[800px]">
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
                            columns={exportTicketTableConfig}
                            items={mapTicketTable(data?.content)}
                            viewAction={goToExportTicketDetailPage}
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

export default ExportPage;
