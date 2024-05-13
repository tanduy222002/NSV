import { cn } from '@renderer/utils/util';
import { ReactNode } from 'react';

type ButtonProps = {
    text?: string;
    className: string;
    type?: 'button' | 'submit';
    icon?: ReactNode;
    action?: (() => any) | ((param: any) => void);
};

const Button = ({
    text,
    className,
    action,
    type = 'button',
    icon
}: ButtonProps) => {
    return (
        <button
            type={type}
            className={cn(
                'flex items-center gap-2 px-2 py-1 border rounded-md font-semibold w-fit',
                className
            )}
            onClick={action}
        >
            {icon}
            <p className="text-base-font-semibold">{text}</p>
        </button>
    );
};

export default Button;
