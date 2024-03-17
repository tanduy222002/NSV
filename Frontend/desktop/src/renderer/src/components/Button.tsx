import { cn } from '@renderer/lib/util';

type ButtonProps = {
    text: string;
    className: string;
    type?: 'button' | 'submit';
    action?: () => any;
};

const Button = ({ text, className, action, type = 'button' }: ButtonProps) => {
    return (
        <button
            type={type}
            className={cn(
                'px-2 py-1 border rounded-md font-semibold w-fit',
                className
            )}
            onClick={action}
        >
            {text}
        </button>
    );
};

export default Button;
