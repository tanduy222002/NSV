import { Rack } from '../type';

type RackLayoutProps = {
    rackLayout: Rack[][];
};

const RackLayout = ({ rackLayout }: RackLayoutProps) => {
    return (
        <div className="flex flex-col">
            {rackLayout.map((rackLine, index) => (
                <div className="flex items-center gap-3" key={index}>
                    {rackLine.map((rack, index) => (
                        <div
                            className="rounded-md border border-[#1A3389] text-[#1A3389] py-1 px-3 font-semibold"
                            key={index}
                        >
                            {rack.name}
                        </div>
                    ))}
                </div>
            ))}
        </div>
    );
};

export default RackLayout;
