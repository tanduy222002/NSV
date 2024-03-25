import { SearchBar } from '@renderer/components';

type FilterSectionProps = {
    title: string;
    options: string[];
};

const FilterSection = ({ title, options }: FilterSectionProps) => {
    return (
        <div className="flex flex-col gap-3 border border-[#E8E8E8] rounded-md py-6 px-6">
            <SearchBar
                placeHolder="Tìm kiếm..."
                className=""
                filterDisabled={true}
            />
            <div>
                <h1 className="text-lg font-semibold mb-2">{title}</h1>
                <div className="flex flex-col gap-2">
                    {options.map((option, i) => (
                        <div key={i} className="flex items-center gap-2">
                            <input type="radio" className="w-[20px] h-[20px]" />
                            <p className="text-base font-semibold">{option}</p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default FilterSection;
