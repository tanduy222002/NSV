import { CgCloseR } from 'react-icons/cg';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useLocalStorage } from '@renderer/hooks';
import { getAvailableBin } from '@renderer/services/api';
import { ImportBinWithSlot } from '@renderer/types/export';
import { Button, TableSkeleton } from '@renderer/components';
import SlotTable from './SlotTable';
import { cn } from '@renderer/utils/util';

type BinSelectorProps = {
    totalWeight: number;
    warehouseId: number;
    qualityId: number;
    closeSelector: () => void;
    addNewBatch: (param: ImportBinWithSlot[]) => void;
};

const BinSelector = ({
    totalWeight,
    warehouseId,
    qualityId,
    closeSelector,
    addNewBatch
}: BinSelectorProps) => {
    const queryClient = useQueryClient();

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['bins', warehouseId, qualityId],
        queryFn: async () => {
            const response = await getAvailableBin({
                token: accessToken,
                pageIndex: 1,
                warehouseId: warehouseId,
                qualityId: qualityId
            });
            return response;
        }
    });

    const getTotalTakenWeight = (bins: ImportBinWithSlot[]) =>
        bins?.reduce((prevWeight, bin) => prevWeight + bin?.taken_weight, 0);

    const updateExportBins = (bins: ImportBinWithSlot[]) => {
        queryClient.setQueryData(['bins', warehouseId, qualityId], () => ({
            ...data,
            content: [...bins]
        }));
    };

    const hanldeConfirm = () => {
        addNewBatch(data?.content);
        closeSelector();
    };

    if (!isFetching) console.log(data);

    return (
        <div className="fixed top-[150px] w-[1200px] mx-auto bg-white shadow px-5 py-5">
            <CgCloseR
                className="w-[20px] h-[20px] hover:text-red-500 ml-auto cursor-pointer"
                onClick={() => closeSelector()}
            />
            <h1 className="mx-auto text-sky-800 text-lg font-semibold">
                Chọn lô hàng muốn xuất kho
            </h1>
            <div className="mb-5">
                <h1 className="text-sky-800 text-base font-semibold">
                    Cần lấy: {totalWeight} kg
                </h1>
                <h1
                    className={cn(
                        'text-base font-semibold',
                        totalWeight === getTotalTakenWeight(data?.content)
                            ? 'text-emerald-500'
                            : 'text-red-500'
                    )}
                >
                    Đã lấy: {getTotalTakenWeight(data?.content)} kg
                </h1>
            </div>
            {isFetching ? (
                <TableSkeleton />
            ) : (
                <SlotTable
                    slots={data?.content}
                    updateExportBins={updateExportBins}
                />
            )}
            <Button
                className="mt-5 mx-auto px-2 py-1 border rounded-md border-sky-800 text-sky-700 hover:bg-sky-100 font-semibold w-fit"
                text="Thêm lô hàng"
                action={hanldeConfirm}
            />
        </div>
    );
};

export default BinSelector;
