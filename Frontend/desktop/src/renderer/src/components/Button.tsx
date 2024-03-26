import { cn } from '@renderer/utils/util';
import { ReactNode } from 'react';
import { ButtonVariant } from './type';

type ButtonProps = {
    text?: string;
    className: string;
    type?: 'button' | 'submit';
    variant?: 'button' | 'icon';
    icon?: ReactNode;
    action?: () => any;
};

const Button = ({
    text,
    className,
    action,
    type = 'button',
    variant = 'button',
    icon
}: ButtonProps) => {
    return (
        <button
            type={type}
            className={cn(
                variant === ButtonVariant.Button
                    ? 'px-2 py-1 border rounded-md font-semibold w-fit'
                    : 'px-2 py-2 border rounded-full',
                className
            )}
            onClick={action}
        >
            {variant === ButtonVariant.Button ? text : icon}
        </button>
    );
};

export default Button;
