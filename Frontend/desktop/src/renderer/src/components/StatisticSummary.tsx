type StatisticSummaryProps = {
    strokeWidth?: number;
    sqSize?: number;
    percentage: number;
};

const StatisticSummary = ({
    sqSize = 160,
    percentage,
    strokeWidth = 8
}: StatisticSummaryProps) => {
    const radius = (sqSize - strokeWidth) / 2;
    const viewBox = `0 0 ${sqSize} ${sqSize}`;
    const dashArray = radius * Math.PI * 2;
    const dashOffset = dashArray - (dashArray * (percentage || 0)) / 100;

    return (
        <div className="relative">
            <svg width={sqSize} height={sqSize} viewBox={viewBox}>
                <circle
                    className="fill-orange-300 stroke-gray-300"
                    cx={sqSize / 2}
                    cy={sqSize / 2}
                    r={radius}
                    strokeWidth={`${strokeWidth}px`}
                />
                <circle
                    className="fill-none stroke-emerald-500 transition-all ease-in delay-200"
                    cx={sqSize / 2}
                    cy={sqSize / 2}
                    r={radius}
                    strokeLinecap="round"
                    strokeWidth={`${strokeWidth}px`}
                    transform={`rotate(-90 ${sqSize / 2} ${sqSize / 2})`}
                    style={{
                        strokeDasharray: dashArray,
                        strokeDashoffset: dashOffset
                    }}
                />
            </svg>
            <p className="absolute top-1/2 left-1/2 -translate-x-[55%] -translate-y-1/2 text-2xl font-semibold whitespace-nowrap">
                {percentage} %
            </p>
        </div>
    );
};

export default StatisticSummary;
