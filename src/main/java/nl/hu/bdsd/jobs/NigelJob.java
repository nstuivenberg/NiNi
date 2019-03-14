package nl.hu.bdsd.jobs;


/**
 * Het systeem werkt nu als volgt:
 * JsonPReader leest het JSON bestand uit en publiceert naar: nick.raw . Er wordt een bericht per seconde gedaan.
 * SanitizeJSON luistert naar nick.raw en doet de volgende stappen:
 * 1. Zet JSON om naar Message-object.
 * 2. Voegt aan het einde van elke htmltag een spatie toe.
 * 3. Haalt alle HTML eruit.
 * 4. Haalt alle woorden eruit die in de stop-list staan.
 * 5. Haalt alle woorden eruit die beginnen met 'http://' of 'https://'
 * 6. publiceert het Message object (met een lege Long) naar nick.clean-data.
 * ZOWEL CorpusJob als TermFrequencyJob luisteren naar nick.clean-data.
 * CorpusJob telt alle woorden die voorkomen in een stream, continue en plaatst deze in een KTable. Deze KTable wordt
 * gepubliseerd naar nick.corpus
 * TermFrequency Job telt alle woorden uit Message.getMessageSource.getSanitizedDescription(). Deze wordt in een HashMap
 * Map<String, MessageCounter> geplaatst. De update van deze stream wordt geplaatst naar nick.tf-data.
 *
 * WAT ER NOG MOET GEBEUREN
 * Er moet een batch-job komen die de resultaten van de TF-job en de Corpus-job combineren.
 * Wanneer de batch-job gaat draaien moet er een soort 'freeze' van de twee streams van dat moment gemaakt worden.
 * Dan kun je voor elk woord in het Message-object De Message counter updaten. Komt het woord voor in de
 * HashMap<String, MessageCounter> dan hoef je alleen maar de methode
 * calculatetfIdf(INT AANTAL KEER DAT HET WOORD IN CORPUS VOORKOMT) aan te roepen en deze up te daten. Al deze resultaten
 * kunnen dan gepost worden naar een topic nigel.is-goed-bezig.
 *
 * Ik zet de wekker om 4 uur om de presentatie te maken. Ik ga eerst even pitten :).
 */
public class NigelJob {
}
