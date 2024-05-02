import { TbPackageExport } from 'react-icons/tb';
import { GiFruitBowl } from 'react-icons/gi';
import { BiCategory } from 'react-icons/bi';
import { BiPackage } from 'react-icons/bi';
import { LiaWpforms } from 'react-icons/lia';
import { FaWeightScale } from 'react-icons/fa6';
import { Button, FormInput, SearchBar } from '@renderer/components';
import { ExportFormStep } from '../type';
import { useModal } from '@renderer/hooks';
import ExportFormFilterStep from './ExportFormFilterStep';

type ExportFormSecondStepProps = {
    goToStep: (step: ExportFormStep) => void;
};
const ExportFormSecondStep = ({ goToStep }: ExportFormSecondStepProps) => {
    const { modalOpen, openModal } = useModal();
    return (
        <>
            <div className="relative w-full">
                <div className="text-xl font-semibold mb-2">Tạo lô hàng</div>
                <SearchBar
                    placeHolder="Tìm theo lô nhập"
                    className="absolute -top-12 right-0"
                    filterAction={() => {
                        alert('modal');
                        openModal && openModal();
                    }}
                />
                <div className="flex items-center justify-center gap-10 w-full">
                    <div className="flex flex-col gap-4 flex-1">
                        <FormInput
                            name="Lô xuất"
                            label="Lô xuất"
                            icon={<TbPackageExport />}
                        />
                        <FormInput
                            name="Sản phẩm"
                            icon={<GiFruitBowl />}
                            label="Sản phẩm"
                        />
                        <FormInput
                            name="Loại hàng"
                            icon={<BiCategory />}
                            label="Loại hàng"
                        />
                    </div>
                    <div className="flex flex-col gap-4 flex-1">
                        <FormInput
                            name="Quy cách"
                            icon={<BiPackage />}
                            label="Quy cách"
                        />
                        <FormInput
                            name="Số lượng"
                            icon={<LiaWpforms />}
                            label="Số lượng"
                        />
                        <FormInput
                            name="Khối lượng"
                            icon={<FaWeightScale />}
                            label="Khối lượng"
                        />
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
                        action={() => goToStep(ExportFormStep.First)}
                    />
                    <Button
                        className="text-[#008767] border-[#008767] bg-[#16C098]"
                        text="Xác nhận"
                    />
                </div>
            </div>
            {modalOpen && <ExportFormFilterStep />}
        </>
    );
};

export default ExportFormSecondStep;
