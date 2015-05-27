/*
 * This Work is in the public domain and is provided on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
 * including, without limitation, any warranties or conditions of TITLE,
 * NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE.
 * You are solely responsible for determining the appropriateness of using
 * this Work and assume any risks associated with your use of this Work.
 *
 * This Work includes contributions authored by David E. Jones, not as a
 * "work for hire", who hereby disclaims any copyright to the same.
 */
package org.moqui.entity;

import java.io.Writer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/** Used to write XML entity data from the database to a writer.
 *
 * The document will have a root element like <code>&lt;entity-facade-xml&gt;</code>.
 */
public interface EntityDataWriter {

    public static final FileType XML = FileType.XML;
    public static final FileType JSON = FileType.JSON;

    public enum FileType { XML, JSON }

    EntityDataWriter fileType(FileType ft);

    /** Specify the name of an entity to query and export. Data is queried and exporting from entities in the order they
     * are added by calling this or entityNames() multiple times.
     * @param entityName The entity name
     * @return Reference to this for convenience.
     */
    EntityDataWriter entityName(String entityName);

    /** A List of entity names to query and export. Data is queried and exporting from entities in the order they are
     * specified in this list and other calls to this or entityName().
     * @param entityNames The list of entity names
     * @return Reference to this for convenience.
     */
    EntityDataWriter entityNames(List<String> entityNames);

    /** Should the dependent records of each record be written? If set will include 2 levels of dependents by default,
     * use dependentLevels() to specify a different number of levels.
     * @param dependents Boolean dependents indicator
     * @return Reference to this for convenience.
     */
    EntityDataWriter dependentRecords(boolean dependents);

    /** The number of levels of dependents to include for records written. If set dependentRecords will be considered true.
     * If dependentRecords is set but no level limit is specified all levels found will be written (may be large and not
     * what is desired). */
    EntityDataWriter dependentLevels(int levels);

    /** A Map of field name, value pairs to filter the results by. Each name/value only used on entities that have a
     * field matching the name.
     * @param filterMap Map with name/value pairs to filter by
     * @return Reference to this for convenience.
     */
    EntityDataWriter filterMap(Map<String, Object> filterMap);

    /** Field names to order (sort) the results by. Each name only used on entities with a field matching the name.
     * May be called multiple times. Each entry may be a comma-separated list of field names.
     * @param orderByList List with field names to order by
     * @return Reference to this for convenience.
     */
    EntityDataWriter orderBy(List<String> orderByList);

    /** From date for lastUpdatedStamp on each entity (lastUpdatedStamp must be greater than or equal (>=) to fromDate).
     * @param fromDate The from date
     * @return Reference to this for convenience.
     */
    EntityDataWriter fromDate(Timestamp fromDate);
    /** Thru date for lastUpdatedStamp on each entity (lastUpdatedStamp must be less than (<) to thruDate).
     * @param thruDate The thru date
     * @return Reference to this for convenience.
     */
    EntityDataWriter thruDate(Timestamp thruDate);

    /** Write all results to a single file.
     * @param filename The path and name of the file to write values to
     * @return Count of values written
     */
    int file(String filename);
    /** Write the results to a file for each entity in the specified directory.
     * @param path The path of the directory to create files in
     * @return Count of values written
     */
    int directory(String path);
    /** Write the results to a Writer.
     * @param writer The Writer to write to
     * @return Count of values written
     */
    int writer(Writer writer);
}
