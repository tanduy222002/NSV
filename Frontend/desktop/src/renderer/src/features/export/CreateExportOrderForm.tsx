import { useState } from 'react';
import { ExportFormStep } from './type';
import { CgCloseR } from 'react-icons/cg';
import { useModal } from '@renderer/hooks';
import {
    ExportFomFilterStep,
    ExportFormFirstStep,
    ExportFormSecondStep,
    FormProgress
} from './components';

const CreateImportOrderForm = () => {
    const [formStep, setFormStep] = useState(ExportFormStep.First);
    const { closeModal } = useModal();

    const goToStep = (step: ExportFormStep) => {
        setFormStep(step);
    };
    return (
        <div className="inset-0 absolute bg-[#C2CDDB] bg-opacity-60 flex items-center justify-center">
            <form className="w-4/5 relative flex flex-col items-center justify-center border border-[#1A3389] rounded-md bg-white py-5 px-8">
                <CgCloseR
                    onClick={closeModal}
                    className="absolute top-5 right-5 text-[#1C274C] cursor-pointer w-[20px] h-[20px]"
                />
                <h1 className="text-xl font-semibold mb-5">Thông tin phiếu</h1>

                {formStep !== ExportFormStep.Filter && (
                    <FormProgress currentStep={formStep} />
                )}

                {formStep === ExportFormStep.First ? (
                    <ExportFormFirstStep goToStep={goToStep} />
                ) : formStep === ExportFormStep.Second ? (
                    <ExportFormSecondStep goToStep={goToStep} />
                ) : (
                    <ExportFomFilterStep />
                )}
            </form>
        </div>
    );
};

export default CreateImportOrderForm;
