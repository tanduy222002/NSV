import { cn } from '@renderer/utils/util';
import { IoSearch } from 'react-icons/io5';
import { GrSort } from 'react-icons/gr';

type SearchBarProps = {
    placeHolder: string;
    className: string;
    filterDisabled?: boolean;
    filterAction?: () => void;
};

const SearchBar = ({
    placeHolder,
    className,
    filterAction,
    filterDisabled = false
}: SearchBarProps) => {
    return (
        <div
            className={cn(
                'flex items-center gap-2 min-w-[250px] max-w-[300px] rounded-sm border border-[#C8C8C8] px-3 py-1',
                className
            )}
        >
            <IoSearch className="cursor-pointer" />
            <input
                className="outline-none flex-1"
                placeholder={placeHolder}
                type="text"
            />
            {!filterDisabled && (
                <GrSort
                    className="ml-auto cursor-pointer hover:text-[#1A3389]"
                    onClick={filterAction}
                />
            )}
        </div>
    );
};

export default SearchBar;
