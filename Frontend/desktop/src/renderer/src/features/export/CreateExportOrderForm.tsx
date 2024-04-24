import { useState } from 'react';
import { ExportFormStep } from './type';
import {
    ExportFormFirstStep,
    ExportFormSecondStep,
    FormProgress
} from './components';
import { ModalProvider } from '@renderer/components';

const CreateExportOrderForm = () => {
    const [formStep, setFormStep] = useState(ExportFormStep.First);

    const goToStep = (step: ExportFormStep) => {
        setFormStep(step);
    };
    return (
        <form className="relative flex flex-col items-center justify-center border border-[#1A3389] rounded-md bg-white py-5 px-8">
            <h1 className="text-xl font-semibold mb-5">Thông tin phiếu</h1>

            {formStep !== ExportFormStep.Filter && (
                <FormProgress currentStep={formStep} />
            )}

            {formStep === ExportFormStep.First ? (
                <ExportFormFirstStep goToStep={goToStep} />
            ) : (
                <ModalProvider>
                    <ExportFormSecondStep goToStep={goToStep} />
                </ModalProvider>
            )}
        </form>
    );
};

export default CreateExportOrderForm;
