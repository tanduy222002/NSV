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
    TableView
} from '@renderer/components';
import { ExportFormStep } from '@renderer/types/export';
import { useLocalStorage } from '@renderer/hooks';
import {
    getProductList,
    getProductCategory,
    getWarehouseDropdown
} from '@renderer/services/api';
import { TextAreaInput } from '@renderer/components';
import { ExportTicket, ImportBinWithSlot } from '@renderer/types/export';
import { ColumnType } from '@renderer/components/TableView';
import { SelectOption } from '@renderer/types/common';
import { createExportTicket } from '@renderer/services/api';
import BinSelector from './BinSelector';

type ExportFormSecondStepProps = {
    exportTicket: ExportTicket;
    updateExportTicket: (value: ExportTicket) => void;
    goToStep: (step: ExportFormStep) => void;
};

const batchTableConfig = [
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
        console.log('warehouses: ', response);
        return response;
    };

    const getProductListCallback = async () => {
        const response = await getProductList({ token: accessToken });
        console.log('products: ', response);
        return response?.content;
    };

    const getProductCategoryCallback = async () => {
        if (product.id === 0) return;
        const response = await getProductCategory({
            token: accessToken,
            productId: product?.id
        });
        console.log('category: ', response);
        return response;
    };

    const addNewBatch = (slots: ImportBinWithSlot[]) => {
        const newExportTicketValue: ExportTicket = {
            ...exportTicket,
            exportBins: [
                ...exportTicket.exportBins,
                {
                    weight: weightRef?.current?.value
                        ? Number(weightRef?.current?.value)
                        : 0,
                    price: priceRef?.current?.value
                        ? Number(priceRef?.current?.value)
                        : 0,
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

    const mapBatchTableData = (ticket: ExportTicket) => {
        return ticket.exportBins.map((bin) => ({
            name: bin?.quality_detail?.name ?? 'Sản phẩm nhập',
            weight: bin?.weight,
            price: bin?.price * bin?.weight,
            packageType: bin?.package_type,
            numberOfSlots: bin?.import_bin_with_slot.length
        }));
    };

    const handleCreateTicket = async (ticket: ExportTicket) => {
        const response = await createExportTicket({
            token: accessToken,
            ticket: ticket
        });
        if (response?.status !== 200) {
            alert('Tạo phiếu thất bại');
        }
    };

    console.log('category: ', productCategory);

    return (
        <>
            <div className="relative w-full">
                <div className="text-xl font-semibold mb-2">Tạo lô hàng</div>
                <div className="flex items-center justify-center gap-10 w-full">
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
                        <FormInput
                            label="Quy cách"
                            name="Quy cách"
                            icon={<LuPackageOpen />}
                            bg="bg-white"
                            ref={packetTypeRef}
                        />
                    </div>
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
                    </div>
                </div>

                {exportTicket?.exportBins.length > 0 && (
                    <div className="flex flex-col items-center">
                        <h2 className="text-lg font-semibold mb-5 self-start">
                            Lô hàng đã tạo
                        </h2>
                        <TableView
                            columns={batchTableConfig}
                            items={mapBatchTableData(exportTicket)}
                        />
                    </div>
                )}

                <Button
                    className="text-emerald-500 border-emerald-500 mt-5 hover:bg-emerald-50"
                    text="Tìm lô hàng..."
                    action={openSelector}
                />
                <div className="flex items-center gap-5 mt-5 w-fit mx-auto">
                    <Button
                        className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                        text="Quay lại"
                        action={() => goToStep(ExportFormStep.First)}
                    />
                    <Button
                        className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                        text="Xác nhận"
                        action={() => handleCreateTicket(exportTicket)}
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
