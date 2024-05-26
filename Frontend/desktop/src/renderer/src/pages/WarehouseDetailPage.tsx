import { IoChevronBack } from 'react-icons/io5';
import { LuBookmark } from 'react-icons/lu';
import { useQuery } from '@tanstack/react-query';
import { useNavigate, useParams } from 'react-router-dom';
import {
    UserInfo,
    TableView,
    StatisticSummary,
    PageLoading
} from '@renderer/components';
import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';
import { WarehouseMapPreview } from '@renderer/features/warehouse/components';
import {
    getWarehouseDetail,
    getWarehouseStatistic
} from '@renderer/services/api';
import { useLocalStorage } from '@renderer/hooks';
import { ColumnType } from '@renderer/components/TableView';

const slotTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tên',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Diện tích',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Trạng thái',
        sortable: false,
        type: ColumnType.Text,
        stylable: true
    },
    {
        title: 'Thao tác',
        sortable: false,
        type: ColumnType.Action
    }
];

const WarehouseDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const goToWarehousePage = () => navigate('/warehouse');
    const goToWarehouseSlotDetailPage = (slotId: number | string) =>
        navigate(`/warehouse/${id}/slot/${slotId}`);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data: warehouseDetail, isFetching: isFetchingDetail } = useQuery({
        queryKey: ['warehouse', id],
        queryFn: () =>
            getWarehouseDetail({
                token: accessToken,
                warehouseId: Number(id as string)
            })
    });

    const { data: warehouseStatistic, isFetching: isFetchingStatistic } =
        useQuery({
            queryKey: ['warehouse', 'statistic'],
            queryFn: () =>
                getWarehouseStatistic({
                    token: accessToken,
                    id: Number(id as string)
                })
        });

    const mapSlotTable = (rows: any[]) => {
        let slots: any[] = [];
        for (const row of rows) {
            console.log('row: ', row);
            const newSlots = row.slots.map((slot) => ({
                id: slot?.id,
                name: `${row?.name}-${slot?.name}`,
                capacity: slot?.capacity,
                status: slot?.status === 'EMPTY' ? 'Trống' : 'Đang sử dụng',
                currentLoad: slot?.currentLoad,
                textColor:
                    slot?.status === 'EMPTY' ? 'text-black' : 'text-emerald-600'
            }));
            slots = [...slots, ...newSlots];
        }
        return slots;
    };

    if (!isFetchingStatistic) console.log('stats: ', warehouseStatistic);

    if (!isFetchingDetail) console.log('detail: ', warehouseDetail);

    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToWarehousePage}
                />
                <img src={warehouseIconSrc} />
                <h1 className="font-semibold text-xl">
                    Chi tiết kho / {warehouseDetail?.name}
                </h1>
            </div>
            {isFetchingDetail ? (
                <PageLoading />
            ) : (
                <div className="flex gap-5">
                    <div>
                        <WarehouseMapPreview
                            warehouseMap={{
                                name: warehouseDetail.name,
                                rows: warehouseDetail?.map.rows
                            }}
                        />
                        <TableView
                            columns={slotTableConfig}
                            items={mapSlotTable(warehouseDetail?.map.rows)}
                            viewAction={goToWarehouseSlotDetailPage}
                        />
                    </div>
                    <div className="flex flex-col mb-5">
                        <div className="flex items-center gap-3 border border-sky-800 rounded-md px-3 py-2">
                            <div>
                                <h1 className="text-lg text-sky-800 font-semibold">
                                    Tổng quan
                                </h1>
                                <div>
                                    <h2>
                                        Tổng diện tích:{' '}
                                        {warehouseDetail?.capacity}
                                    </h2>
                                    <p>
                                        Sử dụng: {warehouseDetail?.containing}
                                    </p>
                                    <p>
                                        Còn trống:{' '}
                                        {warehouseDetail?.capacity -
                                            warehouseDetail?.containing}
                                    </p>
                                </div>
                            </div>
                            <StatisticSummary
                                sqSize={110}
                                percentage={
                                    Math.ceil(
                                        (warehouseDetail?.containing /
                                            warehouseDetail?.capacity) *
                                            10000
                                    ) / 100
                                }
                                strokeWidth={10}
                            />
                        </div>
                        <h1 className="text-lg text-sky-800 font-semibold">
                            Sản phẩm
                        </h1>
                        {warehouseStatistic?.length > 0 ? (
                            <>
                                {warehouseStatistic.map((product, i) => (
                                    <div
                                        key={i}
                                        className="flex items-center gap-3 border border-sky-800 rounded-md px-3 py-3"
                                    >
                                        <div className="w-[80px] h-[80px] overflow-hidden flex items-center justify-center">
                                            <img
                                                src={product?.img}
                                                className="object-cover"
                                            />
                                        </div>
                                        <div>
                                            {product?.type.map(
                                                (category, i) => (
                                                    <div
                                                        key={i}
                                                        className="flex items-center gap-2"
                                                    >
                                                        <LuBookmark />
                                                        <p className="text-sm font-semibold">
                                                            {category?.name}
                                                        </p>
                                                    </div>
                                                )
                                            )}
                                        </div>
                                    </div>
                                ))}
                            </>
                        ) : (
                            <div>Kho đang trống</div>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};

export default WarehouseDetailPage;
