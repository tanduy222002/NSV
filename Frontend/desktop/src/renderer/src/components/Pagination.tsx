import { RiArrowLeftSLine, RiArrowRightSLine } from 'react-icons/ri';
import { cn } from '@renderer/utils/util';

type PaginationProps = {
    maxPage: number;
    currentPage: number;
};

const Pagination = ({ maxPage, currentPage }: PaginationProps) => {
    const pages: number[] = [...Array(maxPage - 1).keys()].map(
        (page: number) => page + 1
    );
    return (
        <div className="flex items-center gap-4 self-end">
            <RiArrowLeftSLine className="border border-gray-400 rounded-md w-[25px] h-[25px] hover:text-white hover:bg-sky-800" />
            {pages.map((page, index) => (
                <p
                    key={index}
                    className={cn(
                        'border border-[#EEEEEE] rounded-md w-[30px] h-[30px] text-center font-semibold',
                        page !== currentPage ? '' : 'bg-sky-800 text-white'
                    )}
                >
                    {page}
                </p>
            ))}
            <RiArrowRightSLine className="border border-gray-400 rounded-md w-[25px] h-[25px] hover:text-white hover:bg-sky-800" />
        </div>
    );
};

export default Pagination;
