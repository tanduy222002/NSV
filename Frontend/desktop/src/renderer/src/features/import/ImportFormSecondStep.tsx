import { useState } from 'react';
import { TbPackageExport } from 'react-icons/tb';
import { GiFruitBowl } from 'react-icons/gi';
import { BiCategory } from 'react-icons/bi';
import { BiPackage } from 'react-icons/bi';
import { LiaWpforms } from 'react-icons/lia';
import { FaWeightScale } from 'react-icons/fa6';
import { Button, FormInput, AsyncSelectInput } from '@renderer/components';
import { ImportFormStep } from './type';
import { useLocalStorage } from '@renderer/hooks';
import { useModal } from '@renderer/hooks';
import { getProductList } from '@renderer/services/api';
import { LuPackageOpen } from 'react-icons/lu';

type ImportFormSecondStepProps = {
    goToStep: (step: ImportFormStep) => void;
};
const ImportFormSecondStep = ({ goToStep }: ImportFormSecondStepProps) => {
    const [product, setProduct] = useState({
        id: 0,
        name: 'Chọn sản phẩm'
    });
    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const getProductListCallback = async () => {
        const response = await getProductList({ token: accessToken });
        console.log('products: ', response);
        return response?.content;
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
                        <FormInput
                            label="Quy cách"
                            name="Quy cách"
                            icon={<LuPackageOpen />}
                            bg="bg-white"
                        />
                        <FormInput
                            label="Số lượng"
                            name="Số lượng"
                            icon={<BiCategory />}
                            bg="bg-white"
                        />
                    </div>
                    <div className="flex flex-col gap-4 flex-1">
                        {/* <FormInput name="Quy cách" icon={<BiPackage />} />
                        <FormInput name="Số lượng" icon={<LiaWpforms />} />
                        <FormInput name="Khối lượng" icon={<FaWeightScale />} /> */}
                    </div>
                </div>
                <Button
                    className="text-[#1A3389] border-[#1A3389] mt-5"
                    text="Thêm mới"
                />
                <div className="flex items-center gap-5 mt-5 w-fit mx-auto">
                    <Button
                        className="text-[#008767] border-[#008767]"
                        text="Quay lại"
                        action={() => goToStep(ImportFormStep.First)}
                    />
                    <Button
                        className="text-[#008767] border-[#008767] bg-[#16C098]"
                        text="Xác nhận"
                    />
                </div>
            </div>
        </>
    );
};

export default ImportFormSecondStep;
