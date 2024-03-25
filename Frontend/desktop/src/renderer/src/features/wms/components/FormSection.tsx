import { ReactNode } from 'react';
import { cn } from '@renderer/lib/util';

type FormSectionProps = {
    title: string;
    icon: ReactNode;
    children?: ReactNode;
    layoutClassName?: string;
};

const FormSection = ({
    title,
    icon,
    children,
    layoutClassName
}: FormSectionProps) => {
    return (
        <div className="my-3">
            <div className="flex items-center gap-2 text-[#7C8DB5] mb-2">
                {icon}
                <h1 className="text-base font-semibold ">{title}</h1>
            </div>
            <div className={cn('w-full', layoutClassName)}>{children}</div>
        </div>
    );
};

export default FormSection;
