package com.roox.medboard.service

import com.roox.medboard.data.model.MedSystem
import com.roox.medboard.data.model.TopicSummary

/**
 * Complete Internal Medicine catalog based on Harrison's 21st Ed & Davidson's 25th Ed.
 * Organized by system with all major topics for Iraqi Board preparation.
 */
object MedicalCatalog {

    val systems = listOf(
        MedSystem("cardio", "Cardiology", "🫀", "#D32F2F", 18, listOf(
            TopicSummary("cardio_ihd", "Ischemic Heart Disease", "ACS, Stable Angina, STEMI, NSTEMI"),
            TopicSummary("cardio_hf", "Heart Failure", "HFrEF, HFpEF, Acute HF, Cardiogenic Shock"),
            TopicSummary("cardio_vhd", "Valvular Heart Disease", "AS, MS, AR, MR, MVP, Prosthetic Valves"),
            TopicSummary("cardio_arrhythmia", "Arrhythmias", "AFib, SVT, VT, Bradycardia, Heart Block"),
            TopicSummary("cardio_htn", "Hypertension", "Primary, Secondary, Hypertensive Crisis, Resistant HTN"),
            TopicSummary("cardio_cm", "Cardiomyopathies", "DCM, HCM, RCM, ARVC, Takotsubo"),
            TopicSummary("cardio_peri", "Pericardial Diseases", "Pericarditis, Tamponade, Constrictive"),
            TopicSummary("cardio_endocarditis", "Infective Endocarditis", "Duke Criteria, Empiric Therapy, Surgical Indications"),
            TopicSummary("cardio_chd", "Congenital Heart Disease in Adults", "ASD, VSD, PDA, Coarctation, Tetralogy"),
            TopicSummary("cardio_pe", "Pulmonary Embolism & DVT", "Wells Score, D-Dimer, Treatment, Anticoagulation"),
            TopicSummary("cardio_pah", "Pulmonary Hypertension", "WHO Classification, Workup, Treatment"),
            TopicSummary("cardio_aortic", "Aortic Diseases", "Aortic Dissection, Aneurysm, Marfan, Takayasu"),
            TopicSummary("cardio_ecg", "ECG Interpretation", "Systematic Approach, Common Patterns, Board Tips"),
            TopicSummary("cardio_shock", "Cardiogenic Shock", "Etiology, Management, IABP, Impella, ECMO"),
            TopicSummary("cardio_lipids", "Dyslipidemia", "Statins, Risk Assessment, Familial Hyperlipidemia"),
            TopicSummary("cardio_devices", "Cardiac Devices", "Pacemakers, ICD, CRT, Leadless"),
            TopicSummary("cardio_rhd", "Rheumatic Heart Disease", "Jones Criteria, Prophylaxis, Management"),
            TopicSummary("cardio_sports", "Sports Cardiology & SCD", "Screening, Sudden Cardiac Death, Return to Play")
        )),

        MedSystem("resp", "Respiratory", "🫁", "#1565C0", 16, listOf(
            TopicSummary("resp_asthma", "Asthma", "Diagnosis, GINA Stepwise, Acute Exacerbation, Severe Asthma"),
            TopicSummary("resp_copd", "COPD", "GOLD Classification, Pharmacotherapy, Exacerbations, O2 Therapy"),
            TopicSummary("resp_pneumonia", "Pneumonia", "CAP, HAP, VAP, Atypical, Aspiration, Empiric Therapy"),
            TopicSummary("resp_tb", "Tuberculosis", "Diagnosis, RIPE Therapy, MDR/XDR-TB, Latent TB, Extrapulmonary"),
            TopicSummary("resp_ild", "Interstitial Lung Disease", "IPF, Sarcoidosis, Hypersensitivity Pneumonitis, CTD-ILD"),
            TopicSummary("resp_pleural", "Pleural Diseases", "Effusion, Empyema, Pneumothorax, Light's Criteria"),
            TopicSummary("resp_bronchiectasis", "Bronchiectasis", "Etiology, CF, Non-CF, Management"),
            TopicSummary("resp_pe", "Pulmonary Embolism", "Diagnosis, Wells, Geneva, CTPA, Treatment, Prevention"),
            TopicSummary("resp_cancer", "Lung Cancer", "NSCLC, SCLC, Staging, Solitary Nodule, Screening"),
            TopicSummary("resp_osa", "Sleep Disorders", "OSA, CSA, Obesity Hypoventilation, CPAP"),
            TopicSummary("resp_ards", "ARDS", "Berlin Criteria, Ventilation Strategy, Proning"),
            TopicSummary("resp_cf", "Cystic Fibrosis", "Diagnosis, CFTR Modulators, Complications"),
            TopicSummary("resp_occ", "Occupational Lung Disease", "Asbestosis, Silicosis, Coal Workers, Mesothelioma"),
            TopicSummary("resp_rf", "Respiratory Failure", "Type 1, Type 2, NIV, Mechanical Ventilation, ABG"),
            TopicSummary("resp_fungal", "Pulmonary Fungal Infections", "Aspergillosis, PCP, Histoplasma, Mucor"),
            TopicSummary("resp_vasculitis", "Pulmonary Vasculitis", "GPA, EGPA, MPA, Goodpasture")
        )),

        MedSystem("gi", "Gastroenterology", "🔥", "#E65100", 16, listOf(
            TopicSummary("gi_gerd", "GERD & Esophageal Disorders", "Barrett's, Achalasia, Eosinophilic Esophagitis"),
            TopicSummary("gi_pud", "Peptic Ulcer Disease", "H. pylori, NSAIDs, Complications, Zollinger-Ellison"),
            TopicSummary("gi_ibd", "Inflammatory Bowel Disease", "Crohn's, UC, Extraintestinal, Biologics"),
            TopicSummary("gi_liver", "Chronic Liver Disease & Cirrhosis", "Etiology, Child-Pugh, MELD, Complications"),
            TopicSummary("gi_hepatitis", "Viral Hepatitis", "HBV, HCV, HAV, HEV, DAAs, Vaccination"),
            TopicSummary("gi_aild", "Autoimmune Liver Disease", "AIH, PBC, PSC, Overlap Syndromes"),
            TopicSummary("gi_pancreatitis", "Pancreatitis", "Acute (Ranson, BISAP), Chronic, Autoimmune"),
            TopicSummary("gi_gi_bleed", "GI Bleeding", "Upper, Lower, Variceal, Obscure, Management"),
            TopicSummary("gi_celiac", "Celiac Disease & Malabsorption", "Diagnosis, Serology, Biopsy, GFD"),
            TopicSummary("gi_ibs", "IBS & Functional GI", "Rome IV, Management, Low FODMAP"),
            TopicSummary("gi_cancer", "GI Malignancies", "Esophageal, Gastric, Colorectal, HCC, Pancreatic"),
            TopicSummary("gi_nafld", "NAFLD / MASLD", "Staging, FIB-4, Management, NASH"),
            TopicSummary("gi_gallbladder", "Biliary Diseases", "Cholelithiasis, Cholecystitis, Cholangitis, Mirizzi"),
            TopicSummary("gi_diarrhea", "Diarrhea", "Acute, Chronic, Infectious, Secretory, Osmotic"),
            TopicSummary("gi_motility", "GI Motility Disorders", "Gastroparesis, Pseudo-obstruction, Hirschsprung"),
            TopicSummary("gi_nutrition", "Clinical Nutrition", "Enteral, Parenteral, Refeeding Syndrome, Vitamins")
        )),

        MedSystem("nephro", "Nephrology", "🔬", "#6A1B9A", 14, listOf(
            TopicSummary("nephro_aki", "Acute Kidney Injury", "Pre-renal, Intrinsic, Post-renal, KDIGO, Dialysis Indications"),
            TopicSummary("nephro_ckd", "Chronic Kidney Disease", "Staging, Complications, Slowing Progression, Dialysis, Transplant"),
            TopicSummary("nephro_gn", "Glomerulonephritis", "Nephritic, Nephrotic, IgA, FSGS, Membranous, MPGN, Lupus Nephritis"),
            TopicSummary("nephro_electrolytes", "Electrolyte Disorders", "Na, K, Ca, Mg, Phosphate — Diagnosis & Management"),
            TopicSummary("nephro_acid_base", "Acid-Base Disorders", "Metabolic Acidosis/Alkalosis, Respiratory, Mixed, ABG Approach"),
            TopicSummary("nephro_stones", "Nephrolithiasis", "Types, Workup, Management, Prevention"),
            TopicSummary("nephro_uti", "Urinary Tract Infections", "Uncomplicated, Complicated, Pyelonephritis, Catheter-associated"),
            TopicSummary("nephro_tubular", "Tubular & Interstitial Disease", "ATN, AIN, RTA Types, Fanconi"),
            TopicSummary("nephro_pckd", "Polycystic Kidney Disease", "ADPKD, ARPKD, Tolvaptan, Screening"),
            TopicSummary("nephro_rvas", "Renovascular Disease", "Renal Artery Stenosis, Atheroembolic, TMA"),
            TopicSummary("nephro_dialysis", "Dialysis", "HD, PD, CRRT, Vascular Access, Complications"),
            TopicSummary("nephro_transplant", "Renal Transplant", "Immunosuppression, Rejection, Complications"),
            TopicSummary("nephro_pregnancy", "Kidney in Pregnancy", "Pre-eclampsia, HELLP, AKI in Pregnancy"),
            TopicSummary("nephro_diabetic", "Diabetic Kidney Disease", "Screening, SGLT2i, Finerenone, Staging")
        )),

        MedSystem("endo", "Endocrinology", "🦋", "#00695C", 14, listOf(
            TopicSummary("endo_dm", "Diabetes Mellitus", "Type 1, Type 2, MODY, Management, Insulin, GLP-1, SGLT2i"),
            TopicSummary("endo_dka", "DKA & HHS", "Diagnosis, Management, Monitoring, Complications"),
            TopicSummary("endo_thyroid", "Thyroid Disorders", "Hypothyroidism, Hyperthyroidism, Graves, Hashimoto, Thyroid Storm"),
            TopicSummary("endo_thyroid_nodule", "Thyroid Nodules & Cancer", "TIRADS, FNA, PTC, FTC, MTC, Anaplastic"),
            TopicSummary("endo_adrenal", "Adrenal Disorders", "Cushing, Addison, Pheochromocytoma, CAH, Incidentaloma"),
            TopicSummary("endo_pituitary", "Pituitary Disorders", "Prolactinoma, Acromegaly, Cushing Disease, DI, Hypopituitarism"),
            TopicSummary("endo_calcium", "Calcium & Bone", "Hyperparathyroidism, Hypoparathyroidism, Osteoporosis, Paget's"),
            TopicSummary("endo_obesity", "Obesity", "BMI, Metabolic Syndrome, Pharmacotherapy, Bariatric"),
            TopicSummary("endo_lipids", "Lipid Disorders", "Familial, Statins, PCSK9i, Risk Calculators"),
            TopicSummary("endo_male", "Male Reproductive Endocrinology", "Hypogonadism, Testosterone, Infertility"),
            TopicSummary("endo_female", "Female Reproductive Endocrinology", "PCOS, Amenorrhea, Menopause, HRT"),
            TopicSummary("endo_mne", "Multiple Endocrine Neoplasia", "MEN1, MEN2A, MEN2B, Screening"),
            TopicSummary("endo_neuroendo", "Neuroendocrine Tumors", "Carcinoid, Insulinoma, Gastrinoma, VIPoma"),
            TopicSummary("endo_hypoglycemia", "Hypoglycemia", "Causes, Whipple Triad, Insulinoma, Factitious")
        )),

        MedSystem("rheum", "Rheumatology", "🦴", "#795548", 12, listOf(
            TopicSummary("rheum_ra", "Rheumatoid Arthritis", "Diagnosis, DMARDs, Biologics, Extra-articular"),
            TopicSummary("rheum_sle", "Systemic Lupus Erythematosus", "SLICC/EULAR Criteria, Renal, CNS, Treatment"),
            TopicSummary("rheum_spondylo", "Spondyloarthropathies", "AS, PsA, Reactive, Enteropathic"),
            TopicSummary("rheum_gout", "Gout & Crystal Arthropathies", "Acute, Chronic, ULT, Pseudogout"),
            TopicSummary("rheum_vasculitis", "Systemic Vasculitis", "Large/Medium/Small Vessel, GPA, PAN, GCA, Takayasu"),
            TopicSummary("rheum_scleroderma", "Systemic Sclerosis", "Limited, Diffuse, Scleroderma Renal Crisis"),
            TopicSummary("rheum_myopathies", "Inflammatory Myopathies", "PM, DM, Inclusion Body, Anti-synthetase"),
            TopicSummary("rheum_sjogren", "Sjögren Syndrome", "Primary, Secondary, Diagnosis, Complications"),
            TopicSummary("rheum_oa", "Osteoarthritis", "Risk Factors, Management, Joint Replacement"),
            TopicSummary("rheum_aps", "Antiphospholipid Syndrome", "Criteria, Thrombosis, Pregnancy, CAPS"),
            TopicSummary("rheum_mixed", "Mixed CTD & Overlap Syndromes", "MCTD, Undifferentiated CTD"),
            TopicSummary("rheum_osteoporosis", "Osteoporosis", "DEXA, FRAX, Bisphosphonates, Denosumab")
        )),

        MedSystem("heme", "Hematology", "🩸", "#B71C1C", 14, listOf(
            TopicSummary("heme_anemia", "Anemia", "IDA, B12/Folate, Hemolytic, Aplastic, Sideroblastic"),
            TopicSummary("heme_leukemia", "Leukemia", "AML, ALL, CML, CLL, Diagnosis, Treatment"),
            TopicSummary("heme_lymphoma", "Lymphoma", "Hodgkin, NHL, Staging, CHOP, ABVD"),
            TopicSummary("heme_myeloma", "Multiple Myeloma", "CRAB, Staging, VRd, Transplant, Amyloidosis"),
            TopicSummary("heme_mpn", "Myeloproliferative Neoplasms", "PV, ET, PMF, CML, JAK2"),
            TopicSummary("heme_mds", "Myelodysplastic Syndromes", "Classification, IPSS, Lenalidomide, Transplant"),
            TopicSummary("heme_coag", "Bleeding Disorders", "Hemophilia, VWD, Platelet Disorders, DIC"),
            TopicSummary("heme_thrombosis", "Thrombotic Disorders", "VTE, Thrombophilia, Anticoagulation, HIT"),
            TopicSummary("heme_transfusion", "Transfusion Medicine", "Products, Reactions, Massive Transfusion"),
            TopicSummary("heme_scd", "Sickle Cell Disease", "Crises, Hydroxyurea, Transfusion, Gene Therapy"),
            TopicSummary("heme_thalassemia", "Thalassemia", "Alpha, Beta, Transfusion, Iron Chelation"),
            TopicSummary("heme_pancytopenia", "Pancytopenia & BM Failure", "Aplastic Anemia, PNH, HLH"),
            TopicSummary("heme_ttp", "TTP/HUS/TMA", "ADAMTS13, Plasmapheresis, aHUS, Eculizumab"),
            TopicSummary("heme_eosinophilia", "Eosinophilia", "HES, Parasitic, Drug, EGPA")
        )),

        MedSystem("id", "Infectious Disease", "🦠", "#2E7D32", 14, listOf(
            TopicSummary("id_sepsis", "Sepsis & Septic Shock", "Sepsis-3, qSOFA, Bundles, Source Control"),
            TopicSummary("id_hiv", "HIV/AIDS", "Diagnosis, ART, OIs, IRIS, PrEP, PEP"),
            TopicSummary("id_fever", "Fever of Unknown Origin", "Classic, Nosocomial, HIV-associated, Workup"),
            TopicSummary("id_meningitis", "CNS Infections", "Bacterial, Viral, TB, Fungal Meningitis, Encephalitis"),
            TopicSummary("id_endocarditis", "Infective Endocarditis", "Duke Criteria, Empiric, HACEK, Prosthetic"),
            TopicSummary("id_sti", "Sexually Transmitted Infections", "Syphilis, Gonorrhea, Chlamydia, HSV, HPV"),
            TopicSummary("id_malaria", "Malaria & Tropical", "P. falciparum, Diagnosis, ACT, Severe Malaria"),
            TopicSummary("id_tb", "Tuberculosis", "Pulmonary, Extrapulmonary, MDR, Latent, TB-HIV"),
            TopicSummary("id_fungal", "Fungal Infections", "Candidiasis, Aspergillosis, Crypto, Histo, Mucor"),
            TopicSummary("id_parasitic", "Parasitic Infections", "Leishmaniasis, Hydatid, Toxo, Strongyloides"),
            TopicSummary("id_hospital", "Hospital-Acquired Infections", "MRSA, VRE, ESBL, CRE, C. diff, Stewardship"),
            TopicSummary("id_antibiotics", "Antimicrobial Therapy", "Selection, PK/PD, De-escalation, Duration"),
            TopicSummary("id_covid", "COVID-19", "Variants, Treatment, Antivirals, Long COVID"),
            TopicSummary("id_zoonotic", "Zoonotic Infections", "Brucellosis, Leptospirosis, Q Fever, Anthrax")
        )),

        MedSystem("neuro", "Neurology", "🧠", "#311B92", 14, listOf(
            TopicSummary("neuro_stroke", "Stroke", "Ischemic, Hemorrhagic, TIA, Thrombolysis, Thrombectomy"),
            TopicSummary("neuro_epilepsy", "Epilepsy & Seizures", "Classification, AEDs, Status Epilepticus, SUDEP"),
            TopicSummary("neuro_headache", "Headache", "Migraine, Tension, Cluster, Secondary, Red Flags"),
            TopicSummary("neuro_ms", "Multiple Sclerosis", "Diagnosis, DMTs, Relapses, PPMS, NMO"),
            TopicSummary("neuro_pd", "Parkinson's & Movement", "PD, Essential Tremor, Atypical Parkinsonism"),
            TopicSummary("neuro_dementia", "Dementia & Cognitive", "AD, Vascular, LBD, FTD, Reversible Causes"),
            TopicSummary("neuro_nmp", "Neuromuscular Disease", "MG, GBS, CIDP, ALS, Muscular Dystrophies"),
            TopicSummary("neuro_cns_infections", "CNS Infections", "Meningitis, Encephalitis, Brain Abscess"),
            TopicSummary("neuro_neuropathy", "Peripheral Neuropathy", "Diabetic, GBS, CIDP, B12, Vasculitic"),
            TopicSummary("neuro_coma", "Altered Consciousness & Coma", "GCS, Approach, Brain Death, Vegetative State"),
            TopicSummary("neuro_tumors", "CNS Tumors", "Glioma, Meningioma, Metastases, Lymphoma"),
            TopicSummary("neuro_myelopathy", "Myelopathy & Spinal Cord", "Compression, Transverse Myelitis, SCD"),
            TopicSummary("neuro_autonomic", "Autonomic Disorders", "Orthostatic Hypotension, POTS, Autonomic Failure"),
            TopicSummary("neuro_vertigo", "Vertigo & Dizziness", "BPPV, Meniere, Vestibular Neuritis, Central")
        )),

        MedSystem("onco", "Oncology", "🎗️", "#880E4F", 10, listOf(
            TopicSummary("onco_principles", "Oncology Principles", "Staging, Performance Status, Chemo Basics, Immunotherapy"),
            TopicSummary("onco_lung", "Lung Cancer", "NSCLC, SCLC, Targeted Therapy, IO, Screening"),
            TopicSummary("onco_breast", "Breast Cancer", "ER/PR/HER2, Staging, Adjuvant, Metastatic"),
            TopicSummary("onco_colorectal", "Colorectal Cancer", "Screening, Lynch, FAP, Staging, Treatment"),
            TopicSummary("onco_emergencies", "Oncologic Emergencies", "TLS, SVC Syndrome, Spinal Cord Compression, Febrile Neutropenia"),
            TopicSummary("onco_paraneoplastic", "Paraneoplastic Syndromes", "SIADH, Cushing, Lambert-Eaton, Dermatomyositis"),
            TopicSummary("onco_unknown", "Cancer of Unknown Primary", "Workup, Treatable Subsets, Management"),
            TopicSummary("onco_palliative", "Palliative & End of Life", "Symptom Management, Pain, Communication"),
            TopicSummary("onco_immunotherapy", "Immunotherapy & Targeted", "Checkpoint Inhibitors, irAEs, TKIs, Biomarkers"),
            TopicSummary("onco_heme_onco", "Hematologic Oncology", "Leukemia, Lymphoma, Myeloma Board Review")
        )),

        MedSystem("derm", "Dermatology", "🩹", "#F57F17", 8, listOf(
            TopicSummary("derm_drug", "Drug Reactions", "SJS/TEN, DRESS, AGEP, Fixed Drug Eruption"),
            TopicSummary("derm_autoimmune", "Autoimmune Skin Disease", "Pemphigus, Pemphigoid, DH, Lupus Skin"),
            TopicSummary("derm_infections", "Skin Infections", "Bacterial, Fungal, Viral, Parasitic"),
            TopicSummary("derm_vasculitis", "Cutaneous Vasculitis", "Leukocytoclastic, HSP, Livedo"),
            TopicSummary("derm_psoriasis", "Psoriasis & Eczema", "Types, Biologics, Topical, Triggers"),
            TopicSummary("derm_internal", "Skin Signs of Internal Disease", "Acanthosis Nigricans, Dermatomyositis, Scleroderma"),
            TopicSummary("derm_melanoma", "Skin Cancer", "Melanoma, BCC, SCC, ABCDE, Staging"),
            TopicSummary("derm_emergencies", "Dermatologic Emergencies", "Necrotizing Fasciitis, SJS/TEN, SSSS")
        )),

        MedSystem("icu", "Critical Care", "🏥", "#37474F", 10, listOf(
            TopicSummary("icu_shock", "Shock", "Distributive, Cardiogenic, Hypovolemic, Obstructive"),
            TopicSummary("icu_ventilation", "Mechanical Ventilation", "Modes, Settings, Weaning, ARDS Ventilation"),
            TopicSummary("icu_sepsis", "Sepsis Management", "Sepsis-3, Hour-1 Bundle, Vasopressors, Steroids"),
            TopicSummary("icu_abg", "ABG & Acid-Base", "Systematic Approach, Mixed Disorders, Compensation"),
            TopicSummary("icu_nutrition", "ICU Nutrition", "Enteral vs Parenteral, Timing, Refeeding"),
            TopicSummary("icu_sedation", "Sedation & Analgesia", "RASS, CAM-ICU, Delirium, Protocols"),
            TopicSummary("icu_poisoning", "Poisoning & Toxicology", "Toxidromes, Antidotes, Decontamination"),
            TopicSummary("icu_brain_death", "Brain Death & Organ Donation", "Criteria, Testing, Ethical Considerations"),
            TopicSummary("icu_procedures", "ICU Procedures", "Central Lines, Intubation, Chest Tubes, ABG"),
            TopicSummary("icu_electrolytes", "Critical Electrolyte Emergencies", "Severe HypoNa, HyperK, HypoCa, HypoMg")
        )),

        MedSystem("geri", "Geriatrics", "👴", "#546E7A", 6, listOf(
            TopicSummary("geri_falls", "Falls & Frailty", "Assessment, Prevention, Frailty Scales"),
            TopicSummary("geri_delirium", "Delirium", "CAM, Causes, Prevention, Management"),
            TopicSummary("geri_polypharmacy", "Polypharmacy", "Beers Criteria, Deprescribing, Drug Interactions"),
            TopicSummary("geri_dementia", "Dementia in Elderly", "AD, Vascular, LBD, Behavioral Management"),
            TopicSummary("geri_incontinence", "Urinary Incontinence", "Types, Workup, Management"),
            TopicSummary("geri_nutrition", "Nutrition in Elderly", "Malnutrition, Sarcopenia, Screening")
        )),

        MedSystem("misc", "General Internal Medicine", "📋", "#455A64", 10, listOf(
            TopicSummary("misc_periop", "Perioperative Medicine", "Cardiac Risk, Pulmonary, Anticoagulation, Meds"),
            TopicSummary("misc_allergy", "Allergy & Anaphylaxis", "IgE-mediated, Drug Allergy, Desensitization"),
            TopicSummary("misc_preeclampsia", "Medicine in Pregnancy", "HTN, DM, Thyroid, Drugs in Pregnancy"),
            TopicSummary("misc_travel", "Travel Medicine", "Malaria Prophylaxis, Vaccines, Traveler's Diarrhea"),
            TopicSummary("misc_genetics", "Clinical Genetics", "Screening, Common Genetic Diseases, Counseling"),
            TopicSummary("misc_evidence", "Evidence-Based Medicine", "Study Design, Bias, NNT, Meta-analysis"),
            TopicSummary("misc_ethics", "Medical Ethics", "Autonomy, Beneficence, Capacity, End of Life"),
            TopicSummary("misc_stats", "Biostatistics for Board", "Sensitivity, Specificity, PPV, NPV, Likelihood Ratios"),
            TopicSummary("misc_preventive", "Preventive Medicine", "Screening Guidelines, Vaccination Schedule"),
            TopicSummary("misc_pain", "Pain Management", "WHO Ladder, Opioids, Neuropathic, Chronic Pain")
        ))
    )

    fun getSystem(id: String): MedSystem? = systems.find { it.id == id }
    fun getTopic(systemId: String, topicId: String): TopicSummary? = getSystem(systemId)?.topics?.find { it.id == topicId }
    fun totalTopics(): Int = systems.sumOf { it.topicCount }
    fun search(query: String): List<Pair<MedSystem, TopicSummary>> {
        val q = query.lowercase()
        val results = mutableListOf<Pair<MedSystem, TopicSummary>>()
        for (sys in systems) {
            for (topic in sys.topics) {
                if (topic.title.lowercase().contains(q) || topic.subtitle.lowercase().contains(q) || sys.name.lowercase().contains(q)) {
                    results.add(Pair(sys, topic))
                }
            }
        }
        return results
    }
}
