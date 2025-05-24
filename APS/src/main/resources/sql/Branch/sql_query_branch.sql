SELECT 
    DENSE_RANK() OVER (ORDER BY COUNT(*) DESC) AS Rank, --แสดงลำดับ ex.1, 1, 2, 3
    YEAR(GETDATE()) - 1 AS AppYear,
    RegionCode,
    BranchCode,
    COUNT(*) AS ApplicationCount
FROM Branch
WHERE YEAR(AppDate) = YEAR(GETDATE()) - 1
GROUP BY RegionCode, BranchCode
HAVING COUNT(*) = (
    SELECT MAX(AppCount) --หาค่าจำนวนสูงสุด
    FROM (
        SELECT COUNT(*) AS AppCount --นับจำนวนสูงสุดของแต่ละสาขา ของแต่ละภาค
        FROM Branch AS B2
        WHERE YEAR(B2.AppDate) = YEAR(GETDATE()) - 1
          AND B2.RegionCode = Branch.RegionCode
        GROUP BY BranchCode
    ) AS Sub
)
ORDER BY ApplicationCount DESC;
