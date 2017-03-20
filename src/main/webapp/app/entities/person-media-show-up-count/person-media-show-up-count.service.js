(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonMediaShowUpCount', PersonMediaShowUpCount);

    PersonMediaShowUpCount.$inject = ['$resource', 'DateUtils'];

    function PersonMediaShowUpCount ($resource, DateUtils) {
        var resourceUrl =  'api/person-media-show-up-counts/:id';
        var findByPersonUrl =  'api/person-media-show-up-counts/person/:id';
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
                        data.showUpDate = DateUtils.convertLocalDateFromServer(data.showUpDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.showUpDate = DateUtils.convertLocalDateToServer(copy.showUpDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.showUpDate = DateUtils.convertLocalDateToServer(copy.showUpDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
