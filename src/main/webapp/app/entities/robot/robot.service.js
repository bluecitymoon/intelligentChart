(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('Robot', Robot);

    Robot.$inject = ['$resource', 'DateUtils'];

    function Robot ($resource, DateUtils) {
        var resourceUrl =  'api/robots/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastStart = DateUtils.convertDateTimeFromServer(data.lastStart);
                        data.lastStop = DateUtils.convertDateTimeFromServer(data.lastStop);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
