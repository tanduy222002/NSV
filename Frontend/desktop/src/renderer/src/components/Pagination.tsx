import { RiArrowLeftSLine, RiArrowRightSLine } from 'react-icons/ri';
import { cn } from '@renderer/utils/util';

type PaginationProps = {
    maxPage: number;
    currentPage: number;
    goNext: () => void;
    goBack: () => void;
    goToPage: (page: number) => void;
};

const Pagination = ({
    maxPage,
    currentPage,
    goNext,
    goBack,
    goToPage
}: PaginationProps) => {
    const pages: number[] = [...Array(maxPage).keys()].map(
        (page: number) => page + 1
    );
    return (
        <div className="flex items-center gap-4 self-end">
            <RiArrowLeftSLine
                onClick={() => goBack()}
                className="cursor-pointer border border-gray-400 rounded-md w-[25px] h-[25px] hover:text-white hover:bg-sky-800"
            />
            {pages.map((page) => (
                <p
                    key={page}
                    onClick={() => goToPage(page)}
                    className={cn(
                        'cursor-pointer border border-[#EEEEEE] rounded-md w-[30px] h-[30px] text-center font-semibold',
                        page !== currentPage ? '' : 'bg-sky-800 text-white'
                    )}
                >
                    {page}
                </p>
            ))}
            <RiArrowRightSLine
                onClick={() => goNext()}
                className="cursor-pointer border border-gray-400 rounded-md w-[25px] h-[25px] hover:text-white hover:bg-sky-800"
            />
        </div>
    );
};

export default Pagination;
