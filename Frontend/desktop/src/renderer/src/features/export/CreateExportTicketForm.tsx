import { useState } from 'react';
import { ExportFormStep } from '@renderer/types/export';
import {
    ExportFormFirstStep,
    ExportFormSecondStep,
    FormProgress
} from './components';
import { ExportTicket } from '@renderer/types/export';
import { defaultExportTicketValue } from '@renderer/constants/export';

const CreateExportTicketForm = () => {
    const [formStep, setFormStep] = useState(ExportFormStep.First);
    const [exportTicket, setExportTicket] = useState<ExportTicket>(
        defaultExportTicketValue
    );

    const updateTicketValue = (ticketValue: ExportTicket) => {
        setExportTicket(ticketValue);
    };

    const goToStep = (step: ExportFormStep) => {
        setFormStep(step);
    };
    return (
        <form className="relative flex flex-col items-center justify-center border border-[#1A3389] rounded-md bg-white py-5 px-8">
            <h1 className="text-xl font-semibold mb-5">Thông tin phiếu</h1>

            <FormProgress currentStep={formStep} />

            {formStep === ExportFormStep.First ? (
                <ExportFormFirstStep
                    exportTicket={exportTicket}
                    updateExportTicket={updateTicketValue}
                    goToStep={goToStep}
                />
            ) : (
                <ExportFormSecondStep
                    exportTicket={exportTicket}
                    updateExportTicket={updateTicketValue}
                    goToStep={goToStep}
                />
            )}
        </form>
    );
};

export default CreateExportTicketForm;
