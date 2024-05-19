import Datepicker from 'react-tailwindcss-datepicker';
import { cn } from '@renderer/utils/util';
import { MdOutlineDateRange } from 'react-icons/md';

type DatePickerProps = {
    name: string;
    placeHolder: string;
    value?: any;
    onChange: (value: any) => void;
};

const DatePicker = ({ name, value, onChange }: DatePickerProps) => {
    return (
        <div
            className={cn(
                'relative flex items-center justify-between border border-sky-800 text-gray-900 text-sm rounded-lg w-full dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white'
            )}
        >
            <div className="z-10 bg-white absolute -top-3 left-4 text-[#7C8DB5] flex items-center gap-1 px-2">
                <MdOutlineDateRange />
                <p className="text-sm font-semibold text-sky-800">{name}</p>
            </div>
            <Datepicker
                primaryColor="sky"
                useRange={false}
                asSingle={true}
                onChange={onChange}
                value={value}
            />
        </div>
    );
};

export default DatePicker;
