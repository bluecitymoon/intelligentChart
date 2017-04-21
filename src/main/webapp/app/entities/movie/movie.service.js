(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('Movie', Movie);

    Movie.$inject = ['$resource', 'DateUtils'];

    function Movie ($resource, DateUtils) {
        var resourceUrl =  'api/movies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.runDate = DateUtils.convertLocalDateFromServer(data.runDate);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.runDate = DateUtils.convertLocalDateToServer(copy.runDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.runDate = DateUtils.convertLocalDateToServer(copy.runDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
