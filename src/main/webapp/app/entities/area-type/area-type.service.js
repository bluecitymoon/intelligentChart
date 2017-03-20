(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('AreaType', AreaType);

    AreaType.$inject = ['$resource'];

    function AreaType ($resource) {
        var resourceUrl =  'api/area-types/:id';

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
