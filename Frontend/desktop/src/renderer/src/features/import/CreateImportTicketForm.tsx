import { useState } from 'react';
import { ImportFormStep } from './type';
import FormProgress from './FormProgress';
import ImportFormFirstStep from './ImportFormFirstStep';
import ImportFormSecondStep from './ImportFormSecondStep';
import { ImportTicket } from '@renderer/types/import';
import { defaultImportTicketValue } from '@renderer/constants/import';

const CreateImportTicketForm = () => {
    const [formStep, setFormStep] = useState(ImportFormStep.First);
    const [importTicket, setImportTicket] = useState<ImportTicket>(
        defaultImportTicketValue
    );

    const updateTicketValue = (ticketValue: ImportTicket) => {
        setImportTicket(ticketValue);
    };

    const goToStep = (step: ImportFormStep) => {
        setFormStep(step);
    };
    return (
        <form className="relative flex flex-col items-center justify-center border border-[#1A3389] rounded-md bg-white py-5 px-8">
            <h1 className="text-xl font-semibold mb-5">Thông tin phiếu</h1>

            {formStep !== ImportFormStep.Second && (
                <FormProgress currentStep={formStep} />
            )}

            {formStep === ImportFormStep.First ? (
                <ImportFormFirstStep
                    goToStep={goToStep}
                    importTicket={importTicket}
                    updateImportTicket={updateTicketValue}
                />
            ) : (
                <ImportFormSecondStep
                    goToStep={goToStep}
                    importTicket={importTicket}
                    updateImportTicket={updateTicketValue}
                />
            )}
        </form>
    );
};

export default CreateImportTicketForm;
