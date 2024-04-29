import { ReactNode } from 'react';
import { useState } from 'react';
import { IoIosArrowDown } from 'react-icons/io';

type SelectInputProps = {
    selectedValue: any;
    placeHolder: string;
    icon?: ReactNode;
    values: string[];
    onSelect: (value: any) => void;
};

const SelectInput = ({
    selectedValue,
    placeHolder,
    values,
    icon,
    onSelect
}: SelectInputProps) => {
    const [expanded, setExpanded] = useState(false);
    // const openSelect = () => setExpanded(true);
    const closeSelect = () => setExpanded(false);
    const toggleSelect = () => {
        setExpanded((prev) => !prev);
    };
    const handleSelect = (option) => {
        onSelect(option);
        closeSelect();
    };

    return (
        <div className="relative w-full">
            {/* selected option */}
            <div className="relative flex items-center justify-between bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg  w-full px-4 py-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white">
                <div className="bg-gray-50 flex items-center gap-2 absolute px-2 -top-3 left-2">
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
                    {values?.map((option, i) => (
                        <li
                            className="cursor-pointer py-2 text-center text-sm hover:bg-slate-200 hover:font-semibold"
                            key={i}
                            onClick={() => handleSelect(option)}
                        >
                            {option}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default SelectInput;
