(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonRegionConnection', PersonRegionConnection);

    PersonRegionConnection.$inject = ['$resource'];

    function PersonRegionConnection ($resource) {
        var resourceUrl =  'api/person-region-connections/:id';

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
