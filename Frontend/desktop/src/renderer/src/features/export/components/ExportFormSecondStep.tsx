import { useRef, useState } from 'react';
import { GiFruitBowl } from 'react-icons/gi';
import { LuPackageOpen, LuWarehouse } from 'react-icons/lu';
import { BiSolidCategory } from 'react-icons/bi';
import { FaWeightScale } from 'react-icons/fa6';
import { BiMoney } from 'react-icons/bi';
import { FaRegFileAlt } from 'react-icons/fa';
import {
    Button,
    FormInput,
    AsyncSelectInput,
    TableView,
    ConfirmationPopup,
    InformationPopup,
    Loading
} from '@renderer/components';
import { ExportBin, ExportFormStep } from '@renderer/types/export';
import { useLocalStorage } from '@renderer/hooks';
import {
    getProductList,
    getProductCategory,
    getWarehouseDropdown
} from '@renderer/services/api';
import { TextAreaInput } from '@renderer/components';
import { ExportTicket, ImportBinWithSlot } from '@renderer/types/export';
import { ColumnType } from '@renderer/components/TableView';
import { InfoPopup, ResultPopup, SelectOption } from '@renderer/types/common';
import { createExportTicket } from '@renderer/services/api';
import BinSelector from './BinSelector';
import { formatNumber } from '@renderer/utils/formatText';
import {
    CreateExportTicketResult,
    createExportTicketConfirmPopupData
} from '@renderer/constants/export';
import { useMutation } from '@tanstack/react-query';

type ExportFormSecondStepProps = {
    exportTicket: ExportTicket;
    updateExportTicket: (value: ExportTicket) => void;
    goToStep: (step: ExportFormStep) => void;
};

const batchTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Tên lô hàng',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Khối lượng',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Giá trị',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Quy cách',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Vị trí đã chọn',
        sortable: false,
        type: ColumnType.Text
    }
];

const ExportFormSecondStep = ({
    exportTicket,
    updateExportTicket,
    goToStep
}: ExportFormSecondStepProps) => {
    const [infoPopup, setInfoPopup] = useState<InfoPopup | null>(null);
    const closeInfoPopup = () => setInfoPopup(null);
    const openCreateTicketConfirm = () =>
        setInfoPopup(createExportTicketConfirmPopupData);

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

    const weightRef = useRef<HTMLInputElement>(null);
    const packetTypeRef = useRef<HTMLInputElement>(null);
    const priceRef = useRef<HTMLInputElement>(null);
    const descriptionRef = useRef<HTMLInputElement>(null);
    const [selectorOpen, setSelectorOpen] = useState(false);
    const closeSelector = () => setSelectorOpen(false);
    const openSelector = () => setSelectorOpen(true);
    const [warehouse, setWarehouse] = useState<SelectOption>({
        id: 0,
        name: 'Chọn kho'
    });
    const [product, setProduct] = useState<SelectOption>({
        id: 0,
        name: 'Chọn sản phẩm'
    });
    const [productCategory, setProductCategory] = useState<SelectOption>({
        id: 0,
        name: 'Chọn phân loại'
    });

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const updateCategory = (product) => {
        setProductCategory({ id: product?.quality_id, name: product?.name });
    };

    const getWarehouseDropdownCallback = async () => {
        const response = await getWarehouseDropdown({ token: accessToken });
        return response;
    };

    const getProductListCallback = async () => {
        const response = await getProductList({ token: accessToken });
        return response?.content;
    };

    const getProductCategoryCallback = async () => {
        if (product.id === 0) return;
        const response = await getProductCategory({
            token: accessToken,
            productId: product?.id
        });
        return response;
    };

    const addNewBatch = (slots: ImportBinWithSlot[]) => {
        const newExportTicketValue: ExportTicket = {
            ...exportTicket,
            exportBins: [
                ...exportTicket.exportBins,
                {
                    weight: Number(weightRef?.current?.value ?? '0'),
                    price:
                        Number(priceRef?.current?.value ?? '0') *
                        Number(weightRef?.current?.value ?? '0'),
                    note: descriptionRef?.current?.value ?? '',
                    quality_id: productCategory?.id,
                    quality_detail: productCategory,
                    package_type: packetTypeRef?.current?.value ?? '',
                    import_bin_with_slot: [...slots]
                }
            ]
        };
        console.log('2nd form value: ', newExportTicketValue);
        updateExportTicket(newExportTicketValue);
    };

    const mapBatchTableData = (exportBins: ExportBin[]) =>
        exportBins?.map((bin, id) => ({
            id: id + 1,
            name: bin?.quality_detail?.name ?? 'Sản phẩm nhập',
            weight: `${bin?.weight} kg`,
            price: `${formatNumber(bin?.price)} VND`,
            packageType: bin?.package_type,
            numberOfSlots: bin?.import_bin_with_slot.length
        })) ?? [];

    const createExportTicketMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await createExportTicket(payload);
            return response;
        }
    });

    const handleCreateTicket = async (ticket: ExportTicket) => {
        closeInfoPopup();
        const response = await createExportTicketMutation.mutateAsync({
            token: accessToken,
            ticket: ticket
        });
        console.log('response: ', response);
        if (response?.status === 200) {
            setResultPopup(CreateExportTicketResult.Success);
        }
        if (response?.status >= 400) {
            setResultPopup(CreateExportTicketResult.Error);
        }
    };

    return (
        <>
            <div className="relative w-full">
                {createExportTicketMutation.isPending && <Loading />}
                {infoPopup && (
                    <ConfirmationPopup
                        title={infoPopup.title}
                        body={infoPopup.body}
                        confirmAction={() => handleCreateTicket(exportTicket)}
                        cancelAction={closeInfoPopup}
                    />
                )}
                {resultPopup && (
                    <InformationPopup
                        title={resultPopup.title}
                        body={resultPopup.body}
                        popupType={resultPopup.popupType}
                        closeAction={closeResultPopup}
                    />
                )}
                <div className="text-xl font-semibold mb-2">Tạo lô hàng</div>
                <div className="flex justify-center gap-10 w-full">
                    <div className="flex flex-col gap-4 flex-1">
                        <FormInput
                            label="Khối lượng"
                            name="Khối lượng"
                            icon={<FaWeightScale />}
                            bg="bg-white"
                            ref={weightRef}
                        />

                        <FormInput
                            label="Đơn giá"
                            name="Đơn giá"
                            icon={<BiMoney />}
                            bg="bg-white"
                            ref={priceRef}
                        />
                        <TextAreaInput
                            label="Ghi chú"
                            name="Ghi chú"
                            icon={<FaRegFileAlt />}
                            bg="bg-white"
                            ref={descriptionRef}
                        />
                        <FormInput
                            label="Quy cách"
                            name="Quy cách"
                            icon={<LuPackageOpen />}
                            bg="bg-white"
                            ref={packetTypeRef}
                        />
                    </div>
                    <div className="flex flex-col gap-4 flex-1">
                        <AsyncSelectInput
                            label="warehouses"
                            placeHolder="Kho hàng"
                            icon={<LuWarehouse />}
                            asyncSelectorCallback={getWarehouseDropdownCallback}
                            selectedValue={warehouse?.name}
                            onSelect={setWarehouse}
                        />
                        <AsyncSelectInput
                            label="products"
                            placeHolder="Sản phẩm"
                            icon={<GiFruitBowl />}
                            asyncSelectorCallback={getProductListCallback}
                            selectedValue={product?.name}
                            onSelect={setProduct}
                        />
                        <AsyncSelectInput
                            label="products-category"
                            placeHolder="Phân loại"
                            icon={<BiSolidCategory />}
                            asyncSelectorCallback={getProductCategoryCallback}
                            selectedValue={productCategory?.name}
                            onSelect={updateCategory}
                        />
                        <Button
                            className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                            text="Tìm lô hàng..."
                            action={openSelector}
                        />
                    </div>
                </div>

                {exportTicket?.exportBins.length > 0 && (
                    <div className="flex flex-col items-center">
                        <h2 className="text-lg font-semibold mb-5 self-start">
                            Lô hàng đã tạo
                        </h2>
                        <TableView
                            columns={batchTableConfig}
                            items={mapBatchTableData(exportTicket.exportBins)}
                        />
                    </div>
                )}

                <div className="flex items-center gap-5 mt-5 w-fit mx-auto">
                    <Button
                        className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                        text="Quay lại"
                        action={() => goToStep(ExportFormStep.First)}
                    />
                    <Button
                        className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                        text="Xác nhận"
                        action={() => openCreateTicketConfirm()}
                    />
                </div>
                {selectorOpen && (
                    <BinSelector
                        totalWeight={Number(weightRef!.current!.value)}
                        warehouseId={warehouse.id}
                        qualityId={productCategory.id}
                        closeSelector={closeSelector}
                        addNewBatch={addNewBatch}
                    />
                )}
            </div>
        </>
    );
};

export default ExportFormSecondStep;
