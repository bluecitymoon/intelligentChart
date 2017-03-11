(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonSearchCount', PersonSearchCount);

    PersonSearchCount.$inject = ['$resource', 'DateUtils'];

    function PersonSearchCount ($resource, DateUtils) {
        var resourceUrl =  'api/person-search-counts/:id';
        var findByPersonUrl =  'api/person-search-counts/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.searchDate = DateUtils.convertLocalDateFromServer(data.searchDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.searchDate = DateUtils.convertLocalDateToServer(copy.searchDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.searchDate = DateUtils.convertLocalDateToServer(copy.searchDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
