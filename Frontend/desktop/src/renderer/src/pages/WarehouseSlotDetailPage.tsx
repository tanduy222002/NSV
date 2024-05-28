import { IoChevronBack } from 'react-icons/io5';
import { LuBookmark } from 'react-icons/lu';
import { GoDotFill } from 'react-icons/go';
import { useQuery } from '@tanstack/react-query';
import { useNavigate, useParams } from 'react-router-dom';
import {
    UserInfo,
    TableView,
    TableSkeleton,
    StatisticSummary
} from '@renderer/components';
import warehouseIconSrc from '@renderer/assets/warehouse-icon.png';
import { getSlotDetail, getSlotStatistic } from '@renderer/services/api';
import { useLocalStorage } from '@renderer/hooks';
import { ColumnType } from '@renderer/components/TableView';
import { formatDate } from '@renderer/utils/formatText';

const batchTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tên lô hàng',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Ngày nhập',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Sản phẩm',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Phân loại',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Quy cách',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Khối lượng',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Thao tác',
        sortable: false,
        type: ColumnType.Action
    }
];

const WarehouseSlotDetailPage = () => {
    const { id, slotId } = useParams();
    const navigate = useNavigate();
    const goToWarehouseDetailPage = () => navigate(`/warehouse/${id}`);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data: slotDetail, isFetching: isFetchingSlotDetail } = useQuery({
        queryKey: ['slot', id, slotId],
        queryFn: () =>
            getSlotDetail({
                token: accessToken,
                warehouseId: Number(id),
                slotId: Number(slotId)
            })
    });

    const { data: slotStatistic, isFetching: isFetchingSlotStatistic } =
        useQuery({
            queryKey: ['slot-statistic', id, slotId],
            queryFn: () =>
                getSlotStatistic({
                    token: accessToken,
                    warehouseId: Number(id),
                    slotId: Number(slotId)
                })
        });

    const mapBatchTable = (batches) =>
        batches.map((batch) => ({
            id: batch?.bin_id,
            name: batch?.ticket_name,
            importDate: formatDate(batch?.import_date),
            productName: batch?.product_name,
            category: batch?.product_type,
            packageType: batch?.packaged,
            weight: `${batch?.weight} kg`
        }));

    if (!isFetchingSlotDetail) console.log(slotDetail);
    if (!isFetchingSlotStatistic) console.log(slotStatistic);
    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToWarehouseDetailPage}
                />
                <img src={warehouseIconSrc} />
                <h1 className="font-semibold text-xl">Chi tiết vị trí kho</h1>
            </div>
            <div className="flex gap-5 items-end mb-5">
                <div className="border border-sky-800 rounded-md px-3 py-2 h-fit flex items-center gap-3">
                    <div className="">
                        <h2 className="font-semibold text-xl">
                            Tổng diện tích
                        </h2>
                        <p className="font-semibold text-base">
                            {slotStatistic?.capacity} m²
                        </p>
                        <p className="font-semibold text-sm flex items-center gap-2">
                            <span>
                                <GoDotFill className="translate-y-[1px] text-emerald-500" />
                            </span>
                            Sử dụng: {slotStatistic?.containing} m²
                        </p>
                        <p className="font-semibold text-sm flex items-center gap-2">
                            <span>
                                <GoDotFill className="translate-y-[1px] text-gray-300" />
                            </span>
                            Còn trống:{' '}
                            {slotStatistic?.capacity -
                                slotStatistic?.containing}{' '}
                            m²
                        </p>
                    </div>
                    <StatisticSummary
                        sqSize={120}
                        percentage={
                            Math.ceil(
                                (10000 * Number(slotStatistic?.containing)) /
                                    Number(slotStatistic?.capacity)
                            ) / 100
                        }
                        strokeWidth={10}
                    />
                </div>
                {slotStatistic?.products?.length > 0 ? (
                    <>
                        {slotStatistic?.products.map((product, i) => (
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
                                    {product?.type.map((category, i) => (
                                        <div
                                            key={i}
                                            className="flex items-center gap-2"
                                        >
                                            <LuBookmark />
                                            <p className="text-sm font-semibold">
                                                {category?.name}:{' '}
                                                {category?.weight} kg
                                            </p>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        ))}
                    </>
                ) : (
                    <div>Kho đang trống</div>
                )}
            </div>
            {isFetchingSlotDetail ? (
                <TableSkeleton />
            ) : (
                <TableView
                    columns={batchTableConfig}
                    items={mapBatchTable(slotDetail?.content)}
                />
            )}
        </div>
    );
};

export default WarehouseSlotDetailPage;
