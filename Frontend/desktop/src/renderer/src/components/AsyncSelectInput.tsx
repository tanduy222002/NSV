import { useQuery } from '@tanstack/react-query';
import { ReactNode } from 'react';
import { useState } from 'react';
import { IoIosArrowDown } from 'react-icons/io';
import { ListSkeleton } from '@renderer/components';
import { cn } from '@renderer/utils/util';

type AsyncSelectInputProps = {
    selectedValue: any;
    placeHolder: string;
    label: string;
    icon?: ReactNode;
    asyncSelectorCallback: () => Promise<any>;
    onSelect: (value: any) => void;
    bg?: string;
};

const AsyncSelectInput = ({
    selectedValue,
    placeHolder,
    label,
    icon,
    asyncSelectorCallback,
    onSelect,
    bg = 'bg-white'
}: AsyncSelectInputProps) => {
    const [expanded, setExpanded] = useState(false);
    // const openSelect = () => setExpanded(true);
    const closeSelect = () => setExpanded(false);
    const toggleSelect = () => {
        if (expanded) refetch();
        setExpanded((prev) => !prev);
    };
    const handleSelect = (option) => {
        onSelect(option);
        closeSelect();
    };

    const { data, refetch, isRefetching, isFetching } = useQuery({
        enabled: false,
        queryKey: [label],
        queryFn: asyncSelectorCallback
    });

    return (
        <div
            className="relative w-full"
            tabIndex={0}
            onBlur={() => {
                closeSelect();
            }}
        >
            {/* selected option */}
            <div
                className={cn(
                    'relative flex items-center justify-between border border-sky-800 text-sky-800 text-sm rounded-lg  w-full px-4 py-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white',
                    bg
                )}
            >
                <div
                    className={cn(
                        'flex items-center gap-2 absolute px-2 -top-3 left-2',
                        bg
                    )}
                >
                    {icon}
                    <p className="text-sm font-semibold text-sky-800">
                        {placeHolder}
                    </p>
                </div>
                <p>{selectedValue}</p>
                <IoIosArrowDown
                    className="hover:text-sky-700 w-[20px] h-[20px]"
                    onClick={toggleSelect}
                />
            </div>

            {/* options */}
            {expanded && (
                <ul className="absolute top-10 z-50 overflow-hidden max-h-[170px] overflow-y-scroll mt-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg  w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white">
                    {isFetching || isRefetching ? (
                        <ListSkeleton />
                    ) : data?.length > 0 ? (
                        data?.map((option, i) => (
                            <li
                                className="cursor-pointer py-2 text-center text-sm hover:bg-slate-200 hover:font-semibold"
                                key={i}
                                onClick={() => handleSelect(option)}
                            >
                                {option?.name ?? option}
                            </li>
                        ))
                    ) : (
                        <li className="cursor-pointer py-2 text-center text-sm hover:bg-slate-200 hover:font-semibold">
                            Chưa có lựa chọn nào
                        </li>
                    )}
                </ul>
            )}
        </div>
    );
};

export default AsyncSelectInput;
