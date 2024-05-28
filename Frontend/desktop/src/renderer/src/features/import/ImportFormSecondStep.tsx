import { useRef, useState } from 'react';
import { GiFruitBowl } from 'react-icons/gi';
import { LuPackageOpen } from 'react-icons/lu';
import { BiSolidCategory } from 'react-icons/bi';
import { FaWeightScale, FaMapLocationDot } from 'react-icons/fa6';
import {
    Button,
    FormInput,
    AsyncSelectInput,
    Loading,
    ConfirmationPopup,
    InformationPopup
} from '@renderer/components';
import { BiMoney } from 'react-icons/bi';
import { FaRegFileAlt } from 'react-icons/fa';
import { ImportFormStep } from './type';
import { useLocalStorage } from '@renderer/hooks';
import {
    getProductList,
    getProductCategory,
    searchWarehouseName
} from '@renderer/services/api';
import { ModalProvider, TextAreaInput, TableView } from '@renderer/components';
import SelectSlotStepController from './SelectSlotPopupController';
import { ImportTicket, BinWithSlot } from '@renderer/types/import';
import { ColumnType } from '@renderer/components/TableView';
import { InfoPopup, ResultPopup, SelectOption } from '@renderer/types/common';
import { createImportTicket } from '@renderer/services/api';
import {
    CreateImportTicketResult,
    createImportTicketConfirmPopupData
} from '@renderer/constants/import';
import { useMutation } from '@tanstack/react-query';
import { formatNumber } from '@renderer/utils/formatText';

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
    const [infoPopup, setInfoPopup] = useState<InfoPopup | null>(null);
    const closeInfoPopup = () => setInfoPopup(null);
    const openCreateTicketConfirm = () =>
        setInfoPopup(createImportTicketConfirmPopupData);

    const [resultPopup, setResultPopup] = useState<ResultPopup | null>(null);
    const closeResultPopup = () => setResultPopup(null);

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

    const searchWarehouseNameCallback = async () => {
        const result = await searchWarehouseName({ token: accessToken });
        return result;
    };

    const removeBatch = (batchId: number | string) => {
        const newImportTicketValue: ImportTicket = {
            ...importTicket,
            binDto: importTicket.binDto.filter((_, id) => id !== batchId)
        };
        updateImportTicket(newImportTicketValue);
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
        return (
            importTicket.binDto.map((bin, id) => ({
                id: id + 1,
                name: bin?.quality_detail?.name ?? 'Sản phẩm nhập',
                weight: bin?.weight,
                price: `${formatNumber(bin?.price * bin?.weight)} VND`,
                packageType: bin?.package_type,
                numberOfSlots: bin?.binWithSlot.length
            })) ?? []
        );
    };

    const createImportTicketMutation = useMutation({
        mutationFn: async (payload: any) => {
            const response = await createImportTicket(payload);
            return response;
        }
    });

    const handleCreateTicket = async (ticket: ImportTicket) => {
        const ticketValue = ticket.binDto.reduce(
            (value, bin) => value + bin?.weight * bin.price,
            0
        );
        ticket.value = ticketValue;
        closeInfoPopup();
        const response = await createImportTicketMutation.mutateAsync({
            token: accessToken,
            ticket: ticket
        });
        if (response?.status === 200) {
            setResultPopup(CreateImportTicketResult.Success);
        }
        if (response?.status >= 400) {
            setResultPopup(CreateImportTicketResult.Error);
        }
    };

    return (
        <div className="relative w-full">
            {createImportTicketMutation.isPending && <Loading />}
            {infoPopup && (
                <ConfirmationPopup
                    title={infoPopup.title}
                    body={infoPopup.body}
                    confirmAction={() => handleCreateTicket(importTicket)}
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
                        placeHolder="Chọn kho"
                        asyncSelectorCallback={searchWarehouseNameCallback}
                        onSelect={setWarehouseMap}
                    />
                    <ModalProvider>
                        <SelectSlotStepController
                            totalWeight={Number(
                                weightRef?.current?.value ?? '0'
                            )}
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
                        deleteAction={removeBatch}
                    />
                </div>
            )}

            <div className="flex items-center gap-5 mt-5 w-fit mx-auto">
                <Button
                    className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                    text="Quay lại"
                    action={() => goToStep(ImportFormStep.First)}
                />
                <Button
                    className="bg-emerald-500 text-white hover:bg-emerald-400"
                    text="Xác nhận"
                    action={() => openCreateTicketConfirm()}
                />
            </div>
        </div>
    );
};

export default ImportFormSecondStep;
