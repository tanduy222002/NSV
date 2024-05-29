import { cn } from '@renderer/utils/util';
import { IoSearch } from 'react-icons/io5';

type SearchBarProps = {
    placeHolder: string;
    className?: string;
    updateSearchValue: (value: string) => void;
};

const SearchBar = ({
    placeHolder,
    className,
    updateSearchValue
}: SearchBarProps) => {
    return (
        <div
            className={cn(
                'flex items-center gap-2 min-w-[250px] max-w-[300px] rounded-md border border-gray-300 px-3 py-2',
                className
            )}
        >
            <IoSearch className="" />
            <input
                onChange={(e) => updateSearchValue(e.target.value)}
                className="outline-none flex-1 focus:text-sky-800 focus:font-semibold text-sm text-gray-400"
                placeholder={placeHolder}
                type="text"
            />
        </div>
    );
};

export default SearchBar;
