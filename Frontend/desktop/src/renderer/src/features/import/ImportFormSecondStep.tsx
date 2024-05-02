import { useRef, useState } from 'react';
import { GiFruitBowl } from 'react-icons/gi';
import { LuPackageOpen } from 'react-icons/lu';
import { BiSolidCategory } from 'react-icons/bi';
import { FaWeightScale, FaMapLocationDot } from 'react-icons/fa6';
import {
    Button,
    FormInput,
    AsyncSelectInput,
    DataField
} from '@renderer/components';
import { BiMoney } from 'react-icons/bi';
import { FaRegFileAlt } from 'react-icons/fa';
import { ImportFormStep } from './type';
import { useLocalStorage } from '@renderer/hooks';
import {
    getProductList,
    searchWarehouseMap,
    getProductCategory
} from '@renderer/services/api';
import { ModalProvider, TextAreaInput, TableView } from '@renderer/components';
import SelectSlotStepController from './SelectSlotPopupController';
import { ImportTicket, BinWithSlot } from '@renderer/types/import';
import { ColumnType } from '@renderer/components/TableView';
import { SelectOption } from '@renderer/types/common';
import { createImportTicket } from '@renderer/services/api';

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

type ImportFormSecondStepProps = {
    importTicket: ImportTicket;
    updateImportTicket: (value: ImportTicket) => void;
    goToStep: (step: ImportFormStep) => void;
};

const ImportFormSecondStep = ({
    goToStep,
    importTicket,
    updateImportTicket
}: ImportFormSecondStepProps) => {
    const weightRef = useRef<HTMLInputElement>(null);
    const packetTypeRef = useRef<HTMLInputElement>(null);
    const priceRef = useRef<HTMLInputElement>(null);
    const descriptionRef = useRef<HTMLInputElement>(null);
    const [product, setProduct] = useState<SelectOption>({
        id: 0,
        name: 'Chọn sản phẩm'
    });
    const [productCategory, setProductCategory] = useState<SelectOption>({
        id: 0,
        name: 'Chọn phân loại'
    });

    const [warehouseMap, setWarehouseMap] = useState<SelectOption>({
        id: 0,
        name: 'Chọn sơ đồ kho'
    });

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const updateCategory = (product) => {
        setProductCategory({ id: product?.quality_id, name: product?.name });
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

    const searchWarehouseMapCallback = async () => {
        const result = await searchWarehouseMap({ token: accessToken });
        return result;
    };

    const addNewBatch = (slots: BinWithSlot[]) => {
        const newImportTicketValue: ImportTicket = {
            ...importTicket,
            binDto: [
                ...importTicket.binDto,
                {
                    weight: weightRef?.current?.value
                        ? Number(weightRef?.current?.value)
                        : 0,
                    binWithSlot: [...slots],
                    price: priceRef?.current?.value
                        ? Number(priceRef?.current?.value)
                        : 0,
                    note: descriptionRef?.current?.value ?? '',
                    quality_id: productCategory?.id,
                    quality_detail: productCategory,
                    package_type: packetTypeRef?.current?.value ?? ''
                }
            ]
        };
        console.log('2nd form value: ', newImportTicketValue);
        updateImportTicket(newImportTicketValue);
    };

    const getBatchTableData = (importTicket: ImportTicket) => {
        return importTicket.binDto.map((bin) => ({
            name: bin?.quality_detail?.name ?? 'Sản phẩm nhập',
            weight: bin?.weight,
            price: bin?.price * bin?.weight,
            packageType: bin?.package_type,
            numberOfSlots: bin?.binWithSlot.length
        }));
    };

    const handleCreateTicket = async (ticket: ImportTicket) => {
        const response = await createImportTicket({
            token: accessToken,
            ticket: ticket
        });
        if (response?.status !== 200) {
            alert('Tạo phiếu thất bại');
        }
    };

    return (
        <>
            <div className="relative w-full">
                <div className="text-xl font-semibold mb-2">Tạo lô hàng</div>
                <div className="flex items-center justify-center gap-10 w-full">
                    <div className="flex flex-col gap-4 flex-1">
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
                        <AsyncSelectInput
                            icon={<FaMapLocationDot />}
                            label="warehouse-map"
                            selectedValue={warehouseMap?.name}
                            placeHolder="Chọn sơ đồ"
                            asyncSelectorCallback={searchWarehouseMapCallback}
                            onSelect={setWarehouseMap}
                        />
                        <DataField
                            name="Khu vực"
                            disabled={importTicket?.binDto.length === 0}
                            defaultValue="Chưa tạo lô hàng"
                            value={
                                importTicket?.binDto.length &&
                                `Đã tạo ${importTicket?.binDto.length} lô hàng`
                            }
                        />
                        <ModalProvider>
                            <SelectSlotStepController
                                addNewBatch={addNewBatch}
                                warehouseId={warehouseMap?.id}
                            />
                        </ModalProvider>
                    </div>
                </div>

                {importTicket?.binDto.length > 0 && (
                    <div className="flex flex-col items-center">
                        <h2 className="text-lg font-semibold mb-5 self-start">
                            Lô hàng đã tạo
                        </h2>
                        <TableView
                            columns={batchTableConfig}
                            items={getBatchTableData(importTicket)}
                        />
                    </div>
                )}

                <div className="flex items-center gap-5 mt-5 w-fit mx-auto">
                    <Button
                        className="text-[#008767] border-[#008767]"
                        text="Quay lại"
                        action={() => goToStep(ImportFormStep.First)}
                    />
                    <Button
                        className="text-[#008767] border-[#008767] bg-[#16C098]"
                        text="Xác nhận"
                        action={() => handleCreateTicket(importTicket)}
                    />
                </div>
            </div>
        </>
    );
};

export default ImportFormSecondStep;
