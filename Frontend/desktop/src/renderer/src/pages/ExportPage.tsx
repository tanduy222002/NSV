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
    TableSkeleton
} from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { searchExportTicket } from '@renderer/services/api';

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

    const updateTicketStatus = (status: TicketStatus) =>
        setTicketStatus(status);

    const mapTicket = useCallback(
        (tickets: any[]) =>
            tickets.map((ticket) => ({
                id: ticket?.id,
                name: ticket?.name,
                productCount: `${ticket?.number_of_products} sản phẩm`,
                weight: ticket?.weight,
                exportDate: ticket?.transfer_date
            })),
        []
    );

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();
    const { data, isFetching } = useQuery({
        queryKey: ['import', ticketStatus],
        queryFn: () =>
            searchExportTicket({
                token: accessToken,
                pageIndex: 1,
                status: ticketStatus
            })
    });

    if (!isFetching) {
        console.log('data: ', data);
    }
    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <img src={warehouseIconSrc} />
                <h1 className="font-semibold text-xl">Phiếu xuất</h1>
            </div>
            <Button
                className="mb-5 px-2 py-1 border border-emerald-600 rounded-md text-emerald-600 text-base font-semibold w-fit"
                text="Tạo phiếu"
                action={goToCreateExportTicketPage}
            />
            <div className="flex items-center gap-2">
                <Button
                    className="mb-5 px-2 py-1 border border-sky-800 rounded-md text-sky-800 hover:bg-sky-100 text-base font-semibold w-fit"
                    text="Toàn bộ"
                    action={() => updateTicketStatus(TicketStatus.All)}
                />
                <Button
                    className="mb-5 px-2 py-1 border border-emerald-600 rounded-md text-emerald-600 text-base font-semibold w-fit"
                    text="Đã duyệt"
                    action={() => updateTicketStatus(TicketStatus.Approved)}
                />
                <Button
                    className="mb-5 px-2 py-1 border border-amber-300 rounded-md text-amber-300 hover:bg-amber-50 text-base font-semibold w-fit"
                    text="Chờ duyệt"
                    action={() => updateTicketStatus(TicketStatus.Pending)}
                />
                <Button
                    className="mb-5 px-2 py-1 border border-red-500 rounded-md text-red-500 hover:bg-red-100 text-base font-semibold w-fit"
                    text="Từ chối"
                    action={() => updateTicketStatus(TicketStatus.Rejected)}
                />
            </div>
            <div className="flex flex-col items-center">
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    data?.content.length > 0 && (
                        <TableView
                            columns={exportTicketTableConfig}
                            items={mapTicket(data?.content)}
                            viewAction={goToExportTicketDetailPage}
                        />
                    )
                )}
            </div>
        </div>
    );
};

export default ExportPage;
