import { CgCloseR } from 'react-icons/cg';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { useLocalStorage } from '@renderer/hooks';
import { getAvailableBin } from '@renderer/services/api';
import { ImportBinWithSlot } from '@renderer/types/export';
import { Button } from '@renderer/components';
import SlotTable from './SlotTable';

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

    console.log('bins data: ', data);

    if (!isFetching) console.log(data);

    console.log(warehouseId, qualityId);
    return (
        <div className="fixed top-[150px] w-[1200px] mx-auto bg-white shadow px-5 py-5">
            <CgCloseR
                className="w-[20px] h-[20px] hover:text-red-500 ml-auto cursor-pointer"
                onClick={() => closeSelector()}
            />
            <div>
                <h1>Tổng khối lượng: {totalWeight}</h1>
            </div>
            {!isFetching && data?.content?.length === 0 && (
                <p className="text-base font-semibold">
                    Hiện không có lô hàng nào đáp ứng yêu cầu
                </p>
            )}
            {data?.content?.length > 0 && (
                <SlotTable
                    slots={data?.content}
                    updateExportBins={updateExportBins}
                />
            )}
            <Button
                className="mx-auto px-2 py-1 border rounded-md border-sky-800 text-sky-700 hover:bg-sky-100 font-semibold w-fit"
                text="Thêm lô hàng"
                action={hanldeConfirm}
            />
        </div>
    );
};

export default BinSelector;
