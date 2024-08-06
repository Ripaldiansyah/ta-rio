package ta_rio.spk_count.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ta_rio.spk_count.controller.SPKCountController;
import ta_rio.spk_count.model.SPKCountModel;
import ta_rio.spk_count.view.SPKCountView;
import ta_rio.spk_result.controller.SpkResultController;
import ta_rio.spk_result.model.SpkResultModel;

public class TopsisAlgorithm {

    public TopsisAlgorithm(
            List<Map<Object, Object>> alternativeListMap,
            List<Map<Object, Object>> criteriaListMap,
            String nameSave

    ) {

        this.alternativeListMap = alternativeListMap;
        this.criteriaListMap = criteriaListMap;
        this.spkName = nameSave;

        topsisInit();
        // setTable();

    }

    // proses ke-1 topsis
    void divider() {
        calculateTotals();
        for (int row = 0; row < criteriaListMap.size(); row++) {
            int total = 0;
            for (int column = 0; column < alternativeListMap.size(); column++) {
                total += Math.pow(totals[column][row], 2);
            }

            dividerList.add(Math.sqrt(total));
        }

    }

    // proses ke-2 topsis
    void normalizeDecisionMatrix() {
        calculateTotals();
        for (int row = 0; row < criteriaListMap.size(); row++) {
            for (int column = 0; column < alternativeListMap.size(); column++) {
                double normalize = 0;
                normalize = totals[column][row] / dividerList.get(row);
                normalizeList.add(normalize);
            }
        }
    }

    // proses ke-3 Topsis
    void decisionNormalizationAndWeightedMatrix() {
        for (Map<Object, Object> data : dataCompile) {
            Map<Object, Object> normalAndWeight = new LinkedHashMap<>();
            int index = 0;
            for (Map.Entry<Object, Object> entry : data.entrySet()) {
                if (index > 3) {
                    double normalizeDecisionMatrixAndWeight = (Double) entry.getValue()
                            * (Double) data.get("criteriaWeight");
                    normalAndWeight.put(entry.getKey(), normalizeDecisionMatrixAndWeight);
                }
                index++;

            }
            normalAndWeightList.add(normalAndWeight);
        }
        updateCombine(normalAndWeightList);
    }

    // proses topsis ke-4
    void getMaxAndMin() {
        for (Map<Object, Object> data : dataCompile) {
            Map<Object, Object> maxAndMin = new LinkedHashMap<>();
            List<Double> valueList = new ArrayList<>();
            int index = 0;

            for (Map.Entry<Object, Object> entry : data.entrySet()) {
                if (index > 3) {
                    double normalizeDecisionMatrixAndWeight = (Double) entry.getValue();
                    valueList.add(normalizeDecisionMatrixAndWeight);

                }
                index++;

            }

            String criteriaType = data.get("criteriaType").toString();
            double max;
            double min;
            if (criteriaType.equalsIgnoreCase("Cost")) {
                max = Collections.min(valueList);
                min = Collections.max(valueList);
            } else {
                max = Collections.max(valueList);
                min = Collections.min(valueList);
            }

            maxAndMin.put("max", max);
            maxAndMin.put("min", min);
            maxAndMinListMap.add(maxAndMin);
        }
        updateCombine(dataCompile);

    }

    // proses topsis ke-5
    void idealSolution() {
        List<Double> maxList = new ArrayList<>();
        List<Double> minList = new ArrayList<>();

        double[][] dataArray = new double[criteriaListMap.size()][];

        for (int i = 0; i < criteriaListMap.size(); i++) {
            int index = 0;
            int columnIndex = 0;
            double[] rowTotal = new double[alternativeListMap.size()];
            Map<Object, Object> data = dataCompile.get(i);// keluarin map dari list
            for (Map.Entry<Object, Object> entry : data.entrySet()) {// hasil map di breackdown lagi
                boolean isNotMaxOrMin = entry.getKey().equals("max") || entry.getKey().equals("min");
                if (index > 3 && !isNotMaxOrMin) {
                    rowTotal[columnIndex] = (double) data.get(entry.getKey());
                    columnIndex++;
                }
                index++;
            }
            dataArray[i] = rowTotal;
            maxList.add((double) data.get("max"));
            minList.add((double) data.get("min"));
        }

        for (int row = 0; row < alternativeListMap.size(); row++) {
            double idealPositive = 0;
            double idealNegative = 0;
            for (int column = 0; column < criteriaListMap.size(); column++) {
                idealPositive += Math.pow((maxList.get(column) - dataArray[column][row]), 2);
                idealNegative += Math.pow((minList.get(column) - dataArray[column][row]), 2);

            }
            idealPositiveList.add(Math.sqrt(idealPositive));
            idealNegativeList.add(Math.sqrt(idealNegative));
        }

    }

    // proses topsis ke-6
    void preference() {
        double preference;
        for (int i = 0; i < alternativeListMap.size(); i++) {
            preference = 0;
            double idealNegative = idealNegativeList.get(i);
            double idealPositive = idealPositiveList.get(i);
            preference = idealNegative / (idealNegative + idealPositive);
            preferenceList.add(preference);

        }

    }

    // proses topsis ke-7
    void rank() {
        List<Map<Object, Object>> altenativeRankListMap = new ArrayList<>(alternativeListMap);
        List<Double> sortedList = new ArrayList<>(preferenceList);
        Collections.sort(sortedList, Collections.reverseOrder());

        for (int i = 0; i < preferenceList.size(); i++) {
            Map<Object, Object> rankMap = new LinkedHashMap<>();
            Map<Object, Object> rowData = altenativeRankListMap.get(i);
            rankMap.put("alternativeName", rowData.get("alternativeName"));
            int rank = sortedList.indexOf(preferenceList.get(i)) + 1;
            rankMap.put("rank", rank);

            rankListMap.add(rankMap);
        }

        setTable();
    }

    void setTable() {
        SpkResultModel resultModel = new SpkResultModel();
        resultModel.setRankListMap(rankListMap);
        resultModel.setSpkSaveName(spkName);
        SPKCountView.resultModel = resultModel;
        SpkResultController controllerResult = new SpkResultController();
        controllerResult.saveSpk(resultModel);

    }

    void topsisInit() {
        divider();
        normalizeDecisionMatrix();
        removeDuplicate();
        compileDataToMap();
        decisionNormalizationAndWeightedMatrix();
        getMaxAndMin();
        idealSolution();
        preference();
        rank();
    }

    void calculateTotals() {
        totals = new double[alternativeListMap.size()][];
        criteriaWeightMap.clear();
        for (int i = 0; i < alternativeListMap.size(); i++) {
            Map<Object, Object> data = alternativeListMap.get(i);
            double[] rowTotal = new double[data.keySet().size()];
            int columnIndex = 0;
            for (Object criteriaDivider : data.keySet()) {
                if (!criteriaDivider.toString().equals("alternativeName")) {
                    rowTotal[columnIndex] = Double.parseDouble(data.get(criteriaDivider).toString());

                    String criteriaName = criteriaDivider.toString();
                    model.setCriteriaName(criteriaName);
                    double criteriaWeight = controller.getCriteriaWeight(model);

                    Map<Object, Object> criteria = new LinkedHashMap<>();
                    criteria.put("criteriaName", criteriaName);
                    criteria.put("criteriaWeight", criteriaWeight);
                    criteriaWeightMap.add(criteria);
                    columnIndex++;
                }
            }
            totals[i] = rowTotal;
        }
    }

    void updateCombine(List<Map<Object, Object>> map) {
        List<Map<Object, Object>> combinedList = new ArrayList<>();

        for (int i = 0; i < criteriaWeightMap.size(); i++) {
            Map<Object, Object> combinedMap = new LinkedHashMap<>();

            for (Map.Entry<Object, Object> entry : criteriaWeightMap.get(i).entrySet()) {
                combinedMap.put(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<Object, Object> entry : criteriaListMap.get(i).entrySet()) {
                if (!combinedMap.containsKey(entry.getKey())) {
                    combinedMap.put(entry.getKey(), entry.getValue());
                }
            }
            for (Map.Entry<Object, Object> entry : dividerListMap.get(i).entrySet()) {
                combinedMap.put(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<Object, Object> entry : map.get(i).entrySet()) {
                combinedMap.put(entry.getKey(), entry.getValue());
            }

            if (!maxAndMinListMap.isEmpty()) {
                for (Map.Entry<Object, Object> entry : maxAndMinListMap.get(i).entrySet()) {
                    combinedMap.put(entry.getKey(), entry.getValue());
                }
            }

            combinedList.add(combinedMap);
        }

        dataCompile.clear();
        dataCompile = combinedList;
    }

    void removeDuplicate() {
        Set<Map<Object, Object>> set = new LinkedHashSet<>(criteriaWeightMap);
        criteriaWeightMap.clear();
        criteriaWeightMap = new ArrayList<>(set);
    }

    void compileDataToMap() {

        int index = 0;
        int limitIndex = alternativeListMap.size();
        for (int i = 0; i < dividerList.size(); i++) {
            int indexAlternative = 0;
            Map<Object, Object> criteriaData = new LinkedHashMap<>();
            criteriaData.put("divider", dividerList.get(i));

            for (; index < limitIndex; index++) {
                Map<Object, Object> data = alternativeListMap.get(indexAlternative);
                criteriaData.put(data.get("alternativeName"), normalizeList.get(index));
                indexAlternative++;
            }
            limitIndex += alternativeListMap.size();
            dividerListMap.add(criteriaData);
        }
        updateCombine(dividerListMap);

    }

    List<Map<Object, Object>> maxAndMinListMap = new ArrayList<>();
    List<Double> dividerList = new ArrayList<>();
    List<Double> normalizeList = new ArrayList<>();
    List<Double> preferenceList = new ArrayList<>();
    List<Map<Object, Object>> criteriaWeightMap = new ArrayList<>();
    List<Map<Object, Object>> dataCompile = new ArrayList<>();
    List<Map<Object, Object>> normalAndWeightList = new ArrayList<>();
    List<Map<Object, Object>> rankListMap = new ArrayList<>();
    public List<Map<Object, Object>> alternativeListMap = new ArrayList<>();
    public List<Map<Object, Object>> criteriaListMap = new ArrayList<>();
    List<Map<Object, Object>> dividerListMap = new ArrayList<>();
    double[][] totals;
    public String spkName;
    private SPKCountController controller = new SPKCountController();
    private SPKCountModel model = new SPKCountModel();
    List<Double> idealPositiveList = new ArrayList<>();
    List<Double> idealNegativeList = new ArrayList<>();

}
