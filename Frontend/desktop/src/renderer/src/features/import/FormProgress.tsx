import { ImportFormStep } from './type';
import { cn } from '@renderer/utils/util';

type FormProgressProps = {
    currentStep: ImportFormStep;
};

const FormProgress = ({ currentStep }: FormProgressProps) => {
    return (
        <div className="flex items-center gap-3 self-start mb-5">
            <div className="rounded-full px-2 py-2 font-semibold  w-[25px] h-[25px] flex items-center justify-center text-white bg-[#1A3389]">
                1
            </div>
            <div
                className={cn(
                    'h-[5px] w-[120px] rounded-2xl',
                    currentStep === ImportFormStep.Second
                        ? 'bg-[#1A3389]'
                        : 'bg-[#EFF0F6]'
                )}
            />
            <div
                className={cn(
                    'rounded-full px-2 py-2 font-semibold w-[25px] h-[25px] flex items-center justify-center',
                    currentStep === ImportFormStep.Second
                        ? 'text-white bg-[#1A3389]'
                        : 'border border-[#6F6C90] text-[#6F6C90]'
                )}
            >
                2
            </div>
            <div className="h-[5px] w-[120px] bg-[#EFF0F6] rounded-2xl" />
        </div>
    );
};

export default FormProgress;
