import { cn } from '@renderer/utils/util';
import { ReactNode } from 'react';

type DataFieldProps = {
    icon?: ReactNode;
    name: string;
    value?: string | number;
    defaultValue: string | number;
    disabled: boolean;
};

const DataField = ({
    icon,
    name,
    value,
    defaultValue,
    disabled
}: DataFieldProps) => {
    return (
        <div
            className={cn(
                'relative flex items-center justify-between border border-sky-800 text-gray-900 text-sm rounded-lg w-full px-4 py-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white',
                disabled ? 'bg-gray-50' : 'bg-white'
            )}
        >
            <div
                className={cn(
                    'absolute -top-3 left-4 text-[#7C8DB5] flex items-center gap-1 px-2',
                    disabled ? 'bg-gray-50' : 'bg-white'
                )}
            >
                {icon}
                <p className="text-sm font-semibold text-sky-800">{name}</p>
            </div>
            <p>{value ?? defaultValue}</p>
        </div>
    );
};

export default DataField;
