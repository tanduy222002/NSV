import { FaPenNib, FaRegFileAlt } from 'react-icons/fa';
import { FaUserGear } from 'react-icons/fa6';
import { BiPhoneCall } from 'react-icons/bi';
import { CiLocationOn } from 'react-icons/ci';
import { MdOutlineDateRange } from 'react-icons/md';
import { CiDollar } from 'react-icons/ci';
import { FaWeightScale } from 'react-icons/fa6';
import { Button, FormInput } from '@renderer/components';
import { ExportFormStep } from '../type';

type ExportFormFirstStepProps = {
    goToStep: (step: ExportFormStep) => void;
};

const ExportFormFirstStep = ({ goToStep }: ExportFormFirstStepProps) => {
    return (
        <div className="w-full">
            <div className="text-xl font-semibold mb-2">Phiếu xuất</div>
            <div className="flex items-center justify-center gap-10">
                <div className="flex flex-col gap-4 flex-1">
                    <FormInput name="Tên phiếu" icon={<FaPenNib />} />
                    <FormInput name="Khách hàng" icon={<FaUserGear />} />
                    <FormInput name="Liên hệ" icon={<BiPhoneCall />} />
                    <FormInput name="Địa chỉ" icon={<CiLocationOn />} />
                    <FormInput name="Ngày xuất" icon={<MdOutlineDateRange />} />
                    <FormInput name="Khối lượng" icon={<FaWeightScale />} />
                </div>
                <div className="flex flex-col gap-4 flex-1">
                    <FormInput name="Vận chuyển" icon={<FaPenNib />} />
                    <FormInput name="Giá trị" icon={<CiDollar />} />
                    <FormInput name="Công nợ" icon={<CiDollar />} />
                    <FormInput name="Tiền nợ" icon={<CiDollar />} />
                    <FormInput name="Đáo hạn" icon={<MdOutlineDateRange />} />
                    <FormInput name="Mô tả" icon={<FaRegFileAlt />} />
                </div>
            </div>
            <div className="flex items-center gap-5 mt-5 w-fit mx-auto">
                <Button
                    className="text-[#008767] border-[#008767] bg-[#16C098]"
                    text="Tiếp theo"
                    action={() => goToStep(ExportFormStep.Second)}
                />
            </div>
        </div>
    );
};

export default ExportFormFirstStep;
