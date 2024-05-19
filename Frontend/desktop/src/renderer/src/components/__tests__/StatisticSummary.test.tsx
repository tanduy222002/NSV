import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import StatisticSummary from '../StatisticSummary';

describe('StatisticSummary component', () => {
    test('renders without crashing', () => {
        render(<StatisticSummary percentage={50} />);
        const percentageText = screen.getByText('50%');
        expect(percentageText).toBeInTheDocument();
    });

    test('renders with correct percentage', () => {
        render(<StatisticSummary percentage={75} />);
        const percentageText = screen.getByText('75%');
        expect(percentageText).toBeInTheDocument();
    });

    test('renders with default strokeWidth and sqSize', () => {
        render(<StatisticSummary percentage={25} />);
        const svgElement = screen.getByTestId('statistic-svg');
        expect(svgElement.getAttribute('width')).toBe('160');
        expect(svgElement.getAttribute('height')).toBe('160');
    });

    test('renders with custom strokeWidth and sqSize', () => {
        render(
            <StatisticSummary percentage={10} strokeWidth={6} sqSize={200} />
        );
        const svgElement = screen.getByTestId('statistic-svg');
        expect(svgElement.getAttribute('width')).toBe('200');
        expect(svgElement.getAttribute('height')).toBe('200');
    });
});
