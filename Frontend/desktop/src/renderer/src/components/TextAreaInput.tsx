import { ReactNode, forwardRef } from 'react';
import { cn } from '@renderer/utils/util';

type TextAreaInputProps = {
    value?: string;
    name?: string;
    label: string;
    onChange?: any;
    defaultValue?: string;
    bg?: string;
    icon?: ReactNode;
};

const TextAreaInput = (
    {
        name,
        label,
        icon,
        value,
        bg,
        defaultValue,
        onChange
    }: TextAreaInputProps,
    ref
) => {
    return (
        <div
            className={cn(
                'relative flex items-center justify-between border border-sky-800 text-gray-900 text-sm rounded-lg  w-full px-4 py-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white',
                bg
            )}
        >
            <div
                className={cn(
                    'absolute -top-3 left-4 text-[#7C8DB5] flex items-center gap-1 px-2',
                    bg
                )}
            >
                {icon}
                <p className="text-sm font-semibold text-sky-800">{label}</p>
            </div>
            <textarea
                name={name}
                onChange={onChange}
                value={value}
                defaultValue={defaultValue}
                ref={ref}
                className={cn('w-full outline-none', bg)}
            ></textarea>
        </div>
    );
};

export default forwardRef(TextAreaInput);
