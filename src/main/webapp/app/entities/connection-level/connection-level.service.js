(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('ConnectionLevel', ConnectionLevel);

    ConnectionLevel.$inject = ['$resource'];

    function ConnectionLevel ($resource) {
        var resourceUrl =  'api/connection-levels/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
