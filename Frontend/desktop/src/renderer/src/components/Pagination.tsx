import { RiArrowLeftSLine, RiArrowRightSLine } from 'react-icons/ri';
import { cn } from '@renderer/utils/util';

type PaginationProps = {
    maxPage: number;
    currentPage: number;
};

const Pagination = ({ maxPage, currentPage }: PaginationProps) => {
    const pages: number[] = [...Array(maxPage).keys()];

    if (maxPage < 6)
        return (
            <div className="flex items-center gap-4 self-end">
                <RiArrowLeftSLine className="border border-[#EEEEEE] rounded-md w-[25px] h-[25px]" />
                {pages.map((page, index) => (
                    <p
                        key={index}
                        className={cn(
                            'border border-[#EEEEEE] rounded-md w-[30px] h-[30px] text-center font-semibold',
                            page !== currentPage
                                ? ''
                                : 'bg-[#5932EA] text-white'
                        )}
                    >
                        {page}
                    </p>
                ))}
                <RiArrowRightSLine className="border border-[#EEEEEE] rounded-md w-[25px] h-[25px]" />
            </div>
        );
    return <div>Pagination</div>;
};

export default Pagination;
